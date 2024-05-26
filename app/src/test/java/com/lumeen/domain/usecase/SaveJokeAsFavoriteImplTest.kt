package com.lumeen.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.lumeen.domain.model.Joke
import com.lumeen.utils.simpleJokeDummy
import io.mockk.coVerify
import io.mockk.every
import org.junit.Test
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import java.util.Locale

class SaveJokeAsFavoriteImplTest {

    @Test
    fun `save joke`() = runTest(StandardTestDispatcher()) {

        // arrange
        val jokeRepository: SaveJokeAsFavoriteImpl.JokeRepository = mockk(relaxed = true)
        val saveJokeAsFavorite = SaveJokeAsFavoriteImpl(
            jokeRepository = jokeRepository
        )

        // act
       saveJokeAsFavorite(simpleJokeDummy)

        // assert
        coVerify(exactly = 1) { jokeRepository.save(simpleJokeDummy) }
    }
}
