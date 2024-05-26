package com.lumeen.domain.usecase

import com.lumeen.domain.model.Joke

interface SaveJokeAsFavorite {
    suspend operator fun invoke(joke: Joke)
}

internal class SaveJokeAsFavoriteImpl(
    private val jokeRepository: JokeRepository,
): SaveJokeAsFavorite {

    interface JokeRepository {
        suspend fun save(joke: Joke)
    }
    override suspend fun invoke(joke: Joke) = jokeRepository.save(joke)
}
