package com.lumeen.domain.usecase

import com.lumeen.domain.model.Joke
import com.lumeen.domain.model.error.GetJokeError
import com.lumeen.utils.Result
import kotlinx.coroutines.flow.Flow

interface GetRandomJoke {
    suspend operator fun invoke(): Result<Joke, GetJokeError>
}

internal class GetRandomJokeImpl(
    private val jokeRepository: JokeRepository,
): GetRandomJoke {

    interface JokeRepository {
        suspend fun getRandomJoke(): Result<Joke, GetJokeError>
    }

    override suspend fun invoke(): Result<Joke, GetJokeError> = jokeRepository.getRandomJoke()
}
