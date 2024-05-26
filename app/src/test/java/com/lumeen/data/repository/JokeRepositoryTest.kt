package com.lumeen.data.repository

import com.google.common.truth.Truth.assertThat
import com.lumeen.data.database.dao.JokeDao
import com.lumeen.data.network.JokeService
import com.lumeen.domain.model.error.GetJokeError
import com.lumeen.utils.simpleJokeDataDummy
import com.lumeen.utils.simpleJokeDummy
import com.lumeen.utils.simpleJokeEntityDummy
import com.lumeen.utils.twoPartsJokeDummy
import com.lumeen.utils.twoPartsJokeEntityDummy
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class JokeRepositoryTest {

    @Test
    fun `delete joke by id`() = runTest(StandardTestDispatcher()) {
        // arrange
        val expectedId = 12345L
        val jokeDao: JokeDao = mockk(relaxed = true)
        val jokeService: JokeService = mockk(relaxed = true)
        val repository = JokeRepository(
            jokeDao = jokeDao,
            jokeService = jokeService,
        )

        // act
        repository.deleteJoke(expectedId)

        // assert
        coVerify(exactly = 1) { jokeDao.deleteById(expectedId) }
    }

    @Test
    fun `get favorite joke list`() = runTest(StandardTestDispatcher()) {
        // arrange
        val jokeDao: JokeDao = mockk {
            every { getAll() } returns flowOf(listOf(simpleJokeEntityDummy, twoPartsJokeEntityDummy))
        }
        val jokeService: JokeService = mockk(relaxed = true)
        val repository = JokeRepository(
            jokeDao = jokeDao,
            jokeService = jokeService,
        )

        // act
        val result = repository.getFavoriteJokeList().firstOrNull()

        // assert
        coVerify(exactly = 1) { jokeDao.getAll() }
        assertThat(result).isEqualTo(listOf(simpleJokeDummy, twoPartsJokeDummy))
    }

    @Test
    fun `check if joke exists`() = runTest(StandardTestDispatcher()) {
        // arrange
        val expectedId = 12345L
        val jokeDao: JokeDao = mockk(relaxed = true)
        val jokeService: JokeService = mockk(relaxed = true)
        val repository = JokeRepository(
            jokeDao = jokeDao,
            jokeService = jokeService,
        )

        // act
        repository.deleteJoke(expectedId)

        // assert
        coVerify(exactly = 1) { jokeDao.deleteById(expectedId) }
    }

    @Test
    fun `get random joke success should return joke`() = runTest(StandardTestDispatcher()) {
        // arrange
        val jokeDao: JokeDao = mockk(relaxed = true)
        val jokeService: JokeService = mockk {
            coEvery { getJoke() } returns Response.success(simpleJokeDataDummy)
        }
        val repository = JokeRepository(
            jokeDao = jokeDao,
            jokeService = jokeService,
        )

        // act
        val result = repository.getRandomJoke().success

        // assert
        coVerify(exactly = 1) { jokeService.getJoke() }
        assertThat(result).isEqualTo(simpleJokeDummy)
    }

    @Test
    fun `get random joke http server failure should return error`() = runTest(StandardTestDispatcher()) {
        // arrange
        val jokeDao: JokeDao = mockk(relaxed = true)
        val jokeService: JokeService = mockk {
            coEvery { getJoke() } returns Response.error(500, ResponseBody.create(null, ""))
        }
        val repository = JokeRepository(
            jokeDao = jokeDao,
            jokeService = jokeService,
        )

        // act
        val result = repository.getRandomJoke().error

        // assert
        coVerify(exactly = 1) { jokeService.getJoke() }
        assertThat(result).isEqualTo(GetJokeError.ServerError(500))
    }

    @Test
    fun `get random joke http client failure should return error`() = runTest(StandardTestDispatcher()) {
        // arrange
        val jokeDao: JokeDao = mockk(relaxed = true)
        val jokeService: JokeService = mockk {
            coEvery { getJoke() } returns Response.error(400, ResponseBody.create(null, ""))
        }
        val repository = JokeRepository(
            jokeDao = jokeDao,
            jokeService = jokeService,
        )

        // act
        val result = repository.getRandomJoke().error

        // assert
        coVerify(exactly = 1) { jokeService.getJoke() }
        assertThat(result).isEqualTo(GetJokeError.ClientError(400))
    }
    
    @Test
    fun `get random joke http network failure should return error`() = runTest(StandardTestDispatcher()) {
        // arrange
        val jokeDao: JokeDao = mockk(relaxed = true)
        val jokeService: JokeService = mockk {
            coEvery { getJoke() }.throws(IOException())
        }
        val repository = JokeRepository(
            jokeDao = jokeDao,
            jokeService = jokeService,
        )

        // act
        val result = repository.getRandomJoke().error

        // assert
        coVerify(exactly = 1) { jokeService.getJoke() }
        assertThat(result).isEqualTo(GetJokeError.NetworkError)
    }
    @Test
    fun `get random joke http failure should return error`() = runTest(StandardTestDispatcher()) {
        // arrange
        val jokeDao: JokeDao = mockk(relaxed = true)
        val jokeService: JokeService = mockk {
            coEvery { getJoke() } returns Response.error(999, ResponseBody.create(null, ""))
        }
        val repository = JokeRepository(
            jokeDao = jokeDao,
            jokeService = jokeService,
        )

        // act
        val result = repository.getRandomJoke().error

        // assert
        coVerify(exactly = 1) { jokeService.getJoke() }
        assertThat(result).isInstanceOf(GetJokeError.UnknownError::class.java)
    }

    @Test
    fun `get random joke error should return error`() = runTest(StandardTestDispatcher()) {
        // arrange
        val jokeDao: JokeDao = mockk(relaxed = true)
        val jokeService: JokeService = mockk {
            coEvery { getJoke() }.throws(Exception())
        }
        val repository = JokeRepository(
            jokeDao = jokeDao,
            jokeService = jokeService,
        )

        // act
        val result = repository.getRandomJoke().error

        // assert
        coVerify(exactly = 1) { jokeService.getJoke() }
        assertThat(result).isEqualTo(GetJokeError.UnknownError(null))
    }
}
