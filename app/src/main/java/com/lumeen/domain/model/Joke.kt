package com.lumeen.domain.model

sealed class Joke {

    data class Single(
        override val id: Long,
        val joke: String,
    ): Joke()

    data class TwoParts(
        override val id: Long,
        val setup: String,
        val delivery: String,
    ): Joke()

    abstract val id: Long
}
