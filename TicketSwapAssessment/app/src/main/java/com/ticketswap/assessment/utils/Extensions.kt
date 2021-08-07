package com.ticketswap.assessment.utils

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

fun <T> LiveData<T>.observeNotNull(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    this.observe(lifecycleOwner, { it?.let { it1 -> observer.onChanged(it1) } })
}

fun Fragment.showToast(@StringRes text: Int, duration: Int) {
    activity?.let {
        Toast.makeText(it, text, duration).show()
    }
}

fun Fragment.listenForBackPress(callback: OnBackPressedCallback) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
}