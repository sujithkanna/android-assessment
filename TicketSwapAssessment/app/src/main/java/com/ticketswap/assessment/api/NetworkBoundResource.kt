package com.ticketswap.assessment.api

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
        saveFetchResult(fetch())
        emit(Resource.Success(query()))
    } catch (e: Exception) {
        emit(Resource.Error<ResultType>(e))
    }
}