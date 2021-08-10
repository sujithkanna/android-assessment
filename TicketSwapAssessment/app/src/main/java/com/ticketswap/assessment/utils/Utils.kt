package com.ticketswap.assessment.utils

import kotlinx.coroutines.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

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

fun prettyCount(number: Long?): String {
    number ?: return "0"
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val value = floor(log10(number.toDouble())).toInt()
    val base = value / 3

    val formatter = NumberFormat.getNumberInstance(Locale.US) as DecimalFormat
    return if (value >= 3 && base < suffix.size) {
        formatter.applyPattern("#0.#")
        formatter.format(number / 10.0.pow((base * 3).toDouble())) + suffix[base]
    } else {
        formatter.applyPattern("#,##0")
        formatter.format(number)
    }
}