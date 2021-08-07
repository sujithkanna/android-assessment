package com.ticketswap.assessment.api

sealed class Resource<T>(
    val action: Action,
    val data: T? = null,
    val error: Throwable? = null
) {
    class Loading<T> : Resource<T>(Action.LOADING)
    class Cache<T>(data: T) : Resource<T>(Action.CACHE, data)
    class Success<T>(data: T) : Resource<T>(Action.SUCCESS, data)
    class Error<T>(throwable: Throwable) : Resource<T>(Action.FAILED, null, throwable)
}

enum class Action {
    LOADING, SUCCESS, FAILED, CACHE
}