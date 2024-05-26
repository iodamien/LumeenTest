package com.lumeen.domain.usecase

import io.mockk.coVerify
import org.junit.Test
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest

class DeleteFavoriteJokeImplTest {

    @Test
    fun `delete favorite`() = runTest(StandardTestDispatcher()) {

        // arrange
        val expectedId = 1234L
        val jokeRepository: DeleteFavoriteJokeImpl.JokeRepository = mockk(relaxed = true)
        val deleteFavorite = DeleteFavoriteJokeImpl(
            jokeRepository = jokeRepository
        )

        // act
        deleteFavorite(expectedId)

        // assert
        coVerify(exactly = 1) { jokeRepository.deleteJoke(eq(expectedId)) }
    }
}
