package com.danielwaiguru.weatherapp.domain.utils

import com.squareup.moshi.Json

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error<out T>(
        val errorMessage: String?,
        val data: T? = null
    ) : ResultWrapper<T>()

    data class Loading<out T>(val data: T? = null) : ResultWrapper<T>()
}

data class ErrorResponse(
    @field:Json(name = "cod")
    val code: Int,
    val message: String
)