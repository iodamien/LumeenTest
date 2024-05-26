package com.lumeen.di

import android.content.Context
import androidx.room.Insert
import com.lumeen.data.database.JokeDatabase
import com.lumeen.data.database.dao.JokeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object JokeDatabaseModule {

    private const val DATABASE_NAME = "joke"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): JokeDatabase = JokeDatabase(
        context = context,
        databaseName = DATABASE_NAME,
    )

    @Provides
    @Singleton
    fun provideJokeDao(jokeDatabase: JokeDatabase): JokeDao = jokeDatabase.jokeDao()
}