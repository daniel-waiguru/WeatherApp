package com.danielwaiguru.weatherapp.domain.utils

import com.squareup.moshi.Json

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(
        val errorMessage: String?
    ) : ResultWrapper<Nothing>()

    data class Loading<out T>(val value: T? = null) : ResultWrapper<T>()
}

data class ErrorResponse(
    @field:Json(name = "cod")
    val code: Int,
    val message: String
)