package com.ticketswap.assessment.utils

import kotlinx.coroutines.*

private const val DOUBLE_BACK_PRESS_DURATION = 1500L
fun doubleBackExitStrategy(
    scope: CoroutineScope, callback: (action: BackPress) -> Unit
): () -> Unit {
    var job: Job? = null
    return {
        if (job == null || job?.isCompleted == true) {
            job = scope.launch(Dispatchers.IO) {
                delay(DOUBLE_BACK_PRESS_DURATION)
                scope.launch(Dispatchers.Main) {
                    callback(BackPress.FIRST)
                }
            }
        } else callback(BackPress.LAST)

    }
}

enum class BackPress {
    FIRST, LAST
}

fun <T> throttleLatest(
    intervalMs: Long = 300L, scope: CoroutineScope, callback: (T) -> Unit
): (T) -> Unit {
    var job: Job? = null
    var latestParam: T
    return { param: T ->
        latestParam = param
        if (job == null || job?.isCompleted == true) {
            job = scope.launch {
                delay(intervalMs)
                scope.launch(Dispatchers.Main) { latestParam.let(callback) }
            }
        }
    }
}