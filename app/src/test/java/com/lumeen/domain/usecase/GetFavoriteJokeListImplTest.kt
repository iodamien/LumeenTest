package com.lumeen.domain.usecase

import io.mockk.coVerify
import org.junit.Test
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import com.lumeen.domain.model.Joke
import com.lumeen.utils.simpleJokeDummy
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import java.util.Locale

class GetFavoriteJokeListImplTest {

    @Test
    fun `get favorite list`() = runTest(StandardTestDispatcher()) {

        // arrange
        val jokeRepository: GetFavoriteJokeListImpl.JokeRepository = mockk {
            every { getFavoriteJokeList() } returns flowOf(listOf(simpleJokeDummy))
        }
        val getFavoriteJokeList = GetFavoriteJokeListImpl(
            jokeRepository = jokeRepository
        )

        // act
        val result = getFavoriteJokeList().firstOrNull()

        // assert
        coVerify(exactly = 1) { jokeRepository.getFavoriteJokeList() }
        assertThat(result).isEqualTo(listOf(simpleJokeDummy))
    }
}