package com.lumeen.di

import com.lumeen.data.repository.JokeRepository
import com.lumeen.domain.usecase.DeleteFavoriteJoke
import com.lumeen.domain.usecase.DeleteFavoriteJokeImpl
import com.lumeen.domain.usecase.GetFavoriteJokeList
import com.lumeen.domain.usecase.GetFavoriteJokeListImpl
import com.lumeen.domain.usecase.GetRandomJoke
import com.lumeen.domain.usecase.GetRandomJokeImpl
import com.lumeen.domain.usecase.IsJokeFavorite
import com.lumeen.domain.usecase.IsJokeFavoriteImpl
import com.lumeen.domain.usecase.SaveJokeAsFavorite
import com.lumeen.domain.usecase.SaveJokeAsFavoriteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class JokeDomainModule {

    @Provides
    fun provideRandomJoke(
        repository: JokeRepository
    ): GetRandomJoke {
        return GetRandomJokeImpl(repository)
    }

    @Provides
    fun provideSaveAsJoke(
        repository: JokeRepository,
    ): SaveJokeAsFavorite = SaveJokeAsFavoriteImpl(repository)

    @Provides
    fun provideIsJokeFavorite(
        repository: JokeRepository,
    ): IsJokeFavorite = IsJokeFavoriteImpl(repository)

    @Provides
    fun provideJokeList(
        repository: JokeRepository
    ): GetFavoriteJokeList = GetFavoriteJokeListImpl(
        repository
    )

    @Provides
    fun provideDeleteFavoriteJoke(
        repository: JokeRepository
    ): DeleteFavoriteJoke = DeleteFavoriteJokeImpl(
        repository
    )
}
