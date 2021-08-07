package com.ticketswap.assessment.utils

import androidx.activity.OnBackPressedCallback

class BackPressAction private constructor(private val callback: Runnable) :
    OnBackPressedCallback(true) {

    override fun handleOnBackPressed() = callback.run()

    companion object {
        fun create(callback: Runnable) = BackPressAction(callback)
    }
}