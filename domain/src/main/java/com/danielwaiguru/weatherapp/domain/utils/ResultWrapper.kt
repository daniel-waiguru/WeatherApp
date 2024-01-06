package com.danielwaiguru.weatherapp.domain.utils

import com.squareup.moshi.Json

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(
        val errorMessage: String?
    ) : ResultWrapper<Nothing>()

    data object Loading : ResultWrapper<Nothing>()
}

data class ErrorResponse(
    @field:Json(name = "cod")
    val code: Int,
    val message: String
)