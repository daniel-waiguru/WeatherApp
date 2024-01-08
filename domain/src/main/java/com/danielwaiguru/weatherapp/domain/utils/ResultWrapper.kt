package com.danielwaiguru.weatherapp.domain.utils

import com.squareup.moshi.Json

sealed class ResultWrapper<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : ResultWrapper<T>(data)
    class Error<T>(
        errorMessage: String?,
        data: T? = null
    ) : ResultWrapper<T>(data, errorMessage)

    class Loading<T>(data: T? = null) : ResultWrapper<T>(data)
}

data class ErrorResponse(
    @field:Json(name = "cod")
    val code: Int,
    val message: String
)