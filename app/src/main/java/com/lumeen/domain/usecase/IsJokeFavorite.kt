package com.lumeen.domain.usecase

import kotlinx.coroutines.flow.Flow

interface IsJokeFavorite {
    operator fun invoke(jokeId: Long): Flow<Boolean>
}

internal class IsJokeFavoriteImpl(
    private val jokeRepository: JokeRepository,
): IsJokeFavorite {

    interface JokeRepository {
        fun exists(jokeId: Long): Flow<Boolean>
    }

    override fun invoke(jokeId: Long): Flow<Boolean> = jokeRepository.exists(jokeId)
}
