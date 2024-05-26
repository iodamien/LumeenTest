package com.lumeen.domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import org.junit.Test
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest

class IsJokeFavoriteImplTest {

    @Test
    fun `is joke favorite`() = runTest(StandardTestDispatcher()) {

        // arrange
        val expectedId = 12345L
        val jokeRepository: IsJokeFavoriteImpl.JokeRepository = mockk {
            every { exists(expectedId) } returns flowOf(true)
        }
        val isJokeFavorite = IsJokeFavoriteImpl(
            jokeRepository = jokeRepository
        )

        // act
        val result = isJokeFavorite(expectedId).firstOrNull()

        // assert
        coVerify(exactly = 1) { jokeRepository.exists(eq(expectedId)) }
        assertThat(result).isEqualTo(true)
    }
}