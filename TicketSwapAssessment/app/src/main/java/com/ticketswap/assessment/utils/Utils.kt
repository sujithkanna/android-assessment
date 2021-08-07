package com.ticketswap.assessment.utils

import kotlinx.coroutines.*

private const val DOUBLE_BACK_PRESS_DURATION = 1500L
fun doubleBackExitStrategy(scope: CoroutineScope, callback: (action: BackPress) -> Unit): () -> Unit {
    var job: Job? = null
    return {
        if (job == null || job?.isCompleted == true) {
            job = scope.launch(Dispatchers.Main) {
                callback(BackPress.FIRST)
                delay(DOUBLE_BACK_PRESS_DURATION)
            }
        } else callback(BackPress.LAST)

    }
}

enum class BackPress {
    FIRST, LAST
}