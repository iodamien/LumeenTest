package com.lumeen.data.repository

import com.lumeen.data.database.dao.JokeDao
import com.lumeen.data.mapper.toDomain
import com.lumeen.data.mapper.toEntity
import com.lumeen.data.network.JokeService
import com.lumeen.domain.model.Joke
import com.lumeen.domain.model.error.GetJokeError
import com.lumeen.domain.usecase.DeleteFavoriteJokeImpl
import com.lumeen.domain.usecase.GetFavoriteJokeListImpl
import com.lumeen.domain.usecase.GetRandomJokeImpl
import com.lumeen.domain.usecase.IsJokeFavoriteImpl
import com.lumeen.domain.usecase.SaveJokeAsFavoriteImpl
import com.lumeen.utils.Result
import com.lumeen.utils.mapListNotNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

internal class JokeRepository(
    private val jokeDao: JokeDao,
    private val jokeService: JokeService,
): SaveJokeAsFavoriteImpl.JokeRepository,
    GetRandomJokeImpl.JokeRepository,
    DeleteFavoriteJokeImpl.JokeRepository,
    GetFavoriteJokeListImpl.JokeRepository,
    IsJokeFavoriteImpl.JokeRepository {

    override suspend fun deleteJoke(jokeId: Long) = jokeDao.deleteById(jokeId)

    override fun getFavoriteJokeList(): Flow<List<Joke>> = jokeDao.getAll()
        .mapListNotNull { it.toDomain() }

    override suspend fun save(joke: Joke) {
        jokeDao.insert(joke.toEntity())
    }

    override fun exists(jokeId: Long): Flow<Boolean> = jokeDao.getById(jokeId)
        .map { it != null }

    override suspend fun getRandomJoke(): Result<Joke, GetJokeError> {
        return try {
            val response = jokeService.getJoke()
            return when {
                response.isSuccessful -> {
                    val jokeData = response.body()?.toDomain()
                    if (jokeData != null) Result.Success(jokeData) else Result.Error(
                        GetJokeError.UnknownError("No joke found")
                    )
                }
                response.code() in 400..499 -> {
                    Result.Error(GetJokeError.ClientError(response.code()))
                }
                response.code() in 500.. 599 -> {
                    Result.Error(GetJokeError.ServerError(response.code()))
                }
                else -> {
                    Result.Error(GetJokeError.UnknownError("Http failed with code: ${response.code()}"))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Result.Error(GetJokeError.NetworkError)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(GetJokeError.UnknownError(e.message))
        }
    }
}

