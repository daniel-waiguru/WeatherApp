package com.danielwaiguru.weatherapp.data.utils

import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = channelFlow {
    val data = query().first()

    if (shouldFetch(data)) {
        val loading = launch {
            query().collect { send(ResultWrapper.Loading) }
        }

        try {
            delay(2000)
            saveFetchResult(fetch())
            loading.cancel()
            query().collect { send(ResultWrapper.Success(it)) }
        } catch (t: Throwable) {
            loading.cancel()
            query().collect { send(ResultWrapper.Error(errorMessage = t.message)) }
        }
    } else {
        query().collect { send(ResultWrapper.Success(it)) }
    }
}