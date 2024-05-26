package com.lumeen.domain.usecase

interface DeleteFavoriteJoke {
    suspend operator fun invoke(jokeId: Long)
}

internal class DeleteFavoriteJokeImpl(
    private val jokeRepository: JokeRepository,
): DeleteFavoriteJoke {

    interface JokeRepository {
        suspend fun deleteJoke(jokeId: Long)
    }

    override suspend fun invoke(jokeId: Long) = jokeRepository.deleteJoke(jokeId)
}
