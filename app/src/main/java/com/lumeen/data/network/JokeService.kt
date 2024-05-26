package com.lumeen.data.network

import com.lumeen.data.network.data.JokeData
import retrofit2.Response
import retrofit2.http.GET

interface JokeService {
     @GET("/joke/Programming/Any?safe-mode")
     suspend fun getJoke(): Response<JokeData>
}
