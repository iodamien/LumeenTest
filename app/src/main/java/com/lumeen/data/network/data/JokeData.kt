package com.lumeen.data.network.data

import android.util.Log

sealed class JokeData {
    data class Single(
        override val id: Long,
        val joke: String,
        override val lang: String,
    ): JokeData()
    data class TwoPart(
        override val id: Long,
        val setup: String,
        val delivery: String,
        override val lang: String,
    ): JokeData()

    abstract val id: Long
    abstract val lang: String
}
