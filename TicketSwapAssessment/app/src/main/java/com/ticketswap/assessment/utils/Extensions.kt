package com.ticketswap.assessment.utils

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
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
                continuation.resume(response, null)
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