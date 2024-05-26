package com.lumeen.di

import com.lumeen.data.database.dao.JokeDao
import com.lumeen.data.network.JokeService
import com.lumeen.data.repository.JokeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object JokeRepositoryModule {

    @Provides
    @Singleton
    fun provideJokeRepository(
        jokeDao: JokeDao,
        jokeService: JokeService,
    ): JokeRepository = JokeRepository(
        jokeDao = jokeDao,
        jokeService = jokeService
    )
}
