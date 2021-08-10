package com.ticketswap.assessment.api

import android.security.keystore.UserNotAuthenticatedException
import kotlinx.coroutines.flow.flow
import java.io.IOException

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> ResultType?,
    crossinline fetch: suspend () -> RequestType?,
    crossinline saveFetchResult: suspend (RequestType) -> ResultType,
) = flow {
    val data = query()
    if (data != null) emit(Resource.Cache(data))
    try {
        emit(Resource.Loading<ResultType>())
        val response = fetch()
        if (response == null) {
            emit(Resource.Error<ResultType>(Action.FAILED, IOException("Response came as null")))
        } else {
            emit(Resource.Success(saveFetchResult(response)))
        }
    } catch (e: Exception) {
        if (e is UserNotAuthenticatedException) {
            emit(Resource.Error<ResultType>(Action.UNAUTHORISED, e))
        } else {
            emit(Resource.Error<ResultType>(Action.FAILED, e))
        }
    }
}