package com.ticketswap.assessment.utils

import android.content.res.Resources.getSystem
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target
import com.ticketswap.assessment.api.Action
import com.ticketswap.assessment.api.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okio.IOException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resumeWithException


fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

fun <T> LiveData<T>.observeNotNull(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    this.observe(lifecycleOwner, { it?.let { it1 -> observer.onChanged(it1) } })
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    if (this.hasActiveObservers()) return
    this.observe(lifecycleOwner, observer)
}

fun Fragment.showToast(@StringRes text: Int, duration: Int) {
    activity?.let {
        Toast.makeText(it, text, duration).show()
    }
}

fun Fragment.listenForBackPress(callback: OnBackPressedCallback) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
}

// https://github.com/gildor/kotlin-coroutines-okhttp/blob/master/src/main/kotlin/ru/gildor/coroutines/okhttp/CallAwait.kt
@ExperimentalCoroutinesApi
suspend fun Call.await(recordStack: Boolean = true): Response {
    val callStack = if (recordStack) {
        IOException().apply {
            // Remove unnecessary lines from stacktrace
            // This doesn't remove await$default, but better than nothing
            stackTrace = stackTrace.copyOfRange(1, stackTrace.size)
        }
    } else null

    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                continuation.resume(response) {}
            }

            override fun onFailure(call: Call, e: IOException) {
                if (continuation.isCancelled) return
                callStack?.initCause(e)
                continuation.resumeWithException(callStack ?: e)
            }
        })

        continuation.invokeOnCancellation {
            try {
                cancel()
            } catch (ex: Throwable) {
                //Ignore cancel exception
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun Response.stringReadSuspend(): String {
    return suspendCancellableCoroutine { continuation ->
        val response = body()!!.string()
        this.closeSafely()
        continuation.resume(response) {}
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Moshi.fromJsonSuspend(response: String, clazz: Class<T>): T? {
    return suspendCancellableCoroutine { continuation ->
        try {
            val adapter = this.adapter(clazz)
            val responseData = adapter.fromJson(response)
            continuation.resume(responseData, {})
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}

fun Response.closeSafely() {
    try {
        close()
    } catch (e: Exception) {
    }
}

fun <T> Collection<T>?.append(items: Collection<T>?): List<T> {
    if (this == null && items == null) return listOf()
    return (this ?: listOf()) + (items ?: listOf())
}

fun Int.pxToDp() = (this / getSystem().displayMetrics.density)

fun Int.dpToPx() = (this * getSystem().displayMetrics.density)

fun View.setBackgroundDrawableColor(color: Int) {
    when (background) {
        is ShapeDrawable -> {
            val shapeDrawable = background as ShapeDrawable
            shapeDrawable.paint.color = color
        }
        is GradientDrawable -> {
            val gradientDrawable = background as GradientDrawable
            gradientDrawable.setColor(color)
        }
        is ColorDrawable -> {
            val colorDrawable = background as ColorDrawable
            colorDrawable.color = color
        }
    }
}

fun String.initialtCaps() = this.replaceFirstChar { it.toString().uppercase() }

fun Long?.toTimerString(): String? {
    this ?: return null
    var finalTimerString = ""
    var secondsString = ""

    // Convert total duration into time
    val hours = (this / (1000 * 60 * 60)).toInt()
    val minutes = (this % (1000 * 60 * 60)).toInt() / (1000 * 60)
    val seconds = (this % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
    // Add hours if there
    if (hours > 0) {
        finalTimerString = "$hours:"
    }

    // Prepending 0 to seconds if it is one digit
    secondsString = if (seconds < 10) {
        "0$seconds"
    } else {
        "" + seconds
    }
    finalTimerString = "$finalTimerString$minutes:$secondsString"

    // return timer string
    return finalTimerString
}

fun <T> List<T>?.getOrCreate() = this ?: listOf()

infix fun <T> List<T>?.add(list: List<T>?): List<T> {
    list ?: return this.getOrCreate()
    return this.getOrCreate().plus(list.getOrCreate().asIterable())
}

fun <Class : LiveData<Data>, Data> Class.addSourceTo(mediator: MediatorLiveData<Data>) = apply {
    mediator.addSource(this) { mediator.value = it }
}

fun <T> Flow<T>.hookToLiveData(
    context: CoroutineContext = EmptyCoroutineContext, hook: (data: T) -> Unit
): LiveData<T> = liveData(context) {
    collect {
        emit(it)
        hook(it)
    }
}

fun <T> Flow<Resource<out T?>>.hookToResponse(hook: (data: T) -> Unit) = hookToLiveData {
    if (it.action == Action.SUCCESS) hook(it.data!!)
}

fun View.hideKeyboard(flags: Int? = null) {
    val imm: InputMethodManager = this.context
        .getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, flags ?: 0)
}

fun RecyclerView.addScrollStateChangedListener(
    onScrollStateChanged: (recyclerView: RecyclerView, newState: Int) -> Unit
) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            onScrollStateChanged(recyclerView, newState)
        }
    })
}

fun RequestCreator.into(
    bitmapLoad: (loadedImage: Bitmap) -> Unit,
    bitmapFailed: (loadedImage: Drawable?) -> Unit
) {
    this.into(object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            bitmapLoad(bitmap)
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
            bitmapFailed(errorDrawable)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

        }
    })
}

fun Bitmap.rounded() = RoundedBitmapDrawableFactory.create(getSystem(), this)

fun Drawable.rounded(): RoundedBitmapDrawable {
    val bitmap: Bitmap = Bitmap.createBitmap(this.intrinsicWidth, this.intrinsicHeight, ARGB_8888)
    val canvas = Canvas(bitmap)
    this.setBounds(0, 0, canvas.width, canvas.height)
    this.draw(canvas)
    return bitmap.rounded()
}