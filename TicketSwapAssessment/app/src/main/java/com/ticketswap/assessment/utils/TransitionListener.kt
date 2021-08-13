package com.ticketswap.assessment.utils

import androidx.transition.Transition

class TransitionListener(
    private val onTransitionStart: ((transition: Transition) -> Unit)? = null,
    private val onTransitionEnd: ((transition: Transition) -> Unit)? = null,
) : Transition.TransitionListener {
    override fun onTransitionStart(transition: Transition) {
        onTransitionStart?.invoke(transition)
    }

    override fun onTransitionEnd(transition: Transition) {
        onTransitionEnd?.invoke(transition)
    }

    override fun onTransitionCancel(transition: Transition) {

    }

    override fun onTransitionPause(transition: Transition) {

    }

    override fun onTransitionResume(transition: Transition) {

    }
}