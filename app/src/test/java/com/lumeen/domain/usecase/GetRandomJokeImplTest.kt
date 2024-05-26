package com.lumeen.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.lumeen.domain.model.error.GetJokeError
import com.lumeen.utils.Result
import com.lumeen.utils.simpleJokeDummy
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetRandomJokeImplTest {

    @Test
    fun `get random joke should return a joke`() = runTest(StandardTestDispatcher()) {
        // arrange
        val jokeRepository: GetRandomJokeImpl.JokeRepository = mockk {
            coEvery { getRandomJoke() } returns Result.Success(simpleJokeDummy)
        }

        val getRandomJoke = GetRandomJokeImpl(
            jokeRepository = jokeRepository
        )

        // act
        val result = getRandomJoke().success

        // assert
        assertThat(result).isEqualTo(simpleJokeDummy)
    }

    @Test
    fun `get random joke should return an error`() = runTest(StandardTestDispatcher()) {
        // arrange
        val jokeRepository: GetRandomJokeImpl.JokeRepository = mockk {
            coEvery { getRandomJoke() } returns Result.Error(GetJokeError.NetworkError)
        }

        val getRandomJoke = GetRandomJokeImpl(
            jokeRepository = jokeRepository
        )

        // act
        val result = getRandomJoke().error

        // assert
        assertThat(result).isEqualTo(GetJokeError.NetworkError)
    }

}