package com.lumeen.domain.model.error

sealed class GetJokeError {
    data class ServerError(val code: Int): GetJokeError()
    data class ClientError(val code: Int): GetJokeError()
    data class UnknownError(val message: String?): GetJokeError()
    data object NetworkError: GetJokeError()
}


