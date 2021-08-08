package com.ticketswap.assessment.api

import android.security.keystore.UserNotAuthenticatedException
import kotlinx.coroutines.flow.flow

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> ResultType?,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
) = flow {
    val data = query()
    if (data != null) emit(Resource.Cache(data))
    try {
        emit(Resource.Loading())
        val response = fetch()
        saveFetchResult(response)
        emit(Resource.Success(response))
    } catch (e: Exception) {
        if (e is UserNotAuthenticatedException) {
            emit(Resource.Error<ResultType>(Action.UNAUTHORISED, e))
        } else {
            emit(Resource.Error<ResultType>(Action.FAILED, e))
        }
    }
}