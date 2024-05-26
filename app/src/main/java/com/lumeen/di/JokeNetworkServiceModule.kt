package com.lumeen.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lumeen.data.network.JokeService
import com.lumeen.data.network.data.JokeData
import com.lumeen.data.network.serializer.JokeDataSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object JokeNetworkServiceModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://v2.jokeapi.dev"

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(JokeData::class.java, JokeDataSerializer())
        .create()

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        baseURL: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideJokeService(retrofit: Retrofit): JokeService = retrofit.create(JokeService::class.java)
}
