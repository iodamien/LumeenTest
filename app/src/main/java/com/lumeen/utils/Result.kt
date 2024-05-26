package com.lumeen.utils


sealed class Result<out SUCCESS, out ERROR> {
    data class Success<SUCCESS>(val value: SUCCESS): Result<SUCCESS, Nothing>()
    data class Error<ERROR>(val value: ERROR): Result<Nothing, ERROR>()

    val isSuccess: Boolean
        get() = this is Success

    val isError: Boolean
        get() = this is Error

    val error: ERROR?
        get() = when (this) {
            is Success -> null
            is Error -> value
        }

    val success: SUCCESS?
        get() = when (this) {
            is Success -> value
            is Error -> null
        }
}
