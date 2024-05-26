package com.lumeen.domain.usecase

import com.lumeen.domain.model.Joke
import kotlinx.coroutines.flow.Flow

interface GetFavoriteJokeList {
    operator fun invoke(): Flow<List<Joke>>
}

internal class GetFavoriteJokeListImpl(
    private val jokeRepository: JokeRepository,
): GetFavoriteJokeList {

    interface JokeRepository {
        fun getFavoriteJokeList(): Flow<List<Joke>>
    }

    override fun invoke(): Flow<List<Joke>> = jokeRepository.getFavoriteJokeList()
}
