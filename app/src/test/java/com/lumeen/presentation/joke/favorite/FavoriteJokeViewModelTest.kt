package com.lumeen.presentation.joke.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.lumeen.domain.usecase.GetFavoriteJokeList
import com.lumeen.utils.simpleJokeDummy
import com.lumeen.utils.twoPartsJokeDummy
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteJokeViewModelTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `init should fetch favorite jokes list`() = runTest {
        // arrange
        val jokes = listOf(simpleJokeDummy, twoPartsJokeDummy)
        val getFavoriteJokeList: GetFavoriteJokeList = mockk()
        every { getFavoriteJokeList.invoke() } returns flowOf(jokes)

        // act
        val viewModel = FavoriteJokeViewModel(getFavoriteJokeList, mockk(relaxed = true))

        // assert
        assertThat(viewModel.favoriteJokeList.firstOrNull()).isEqualTo(jokes)
    }
//
    @Test
    fun `deleteJoke should call deleteFavoriteJoke use case`() = runTest {
        // arrange
        val viewModel = FavoriteJokeViewModel(
            getFavoriteJokeList = mockk(relaxed = true),
            deleteFavoriteJoke = mockk(relaxed = true)
        )
        val jokeId = 1L

        // act
        viewModel.deleteJoke(jokeId)

        // assert
        verify(exactly = 1) { viewModel.deleteJoke(1L) }
    }
}