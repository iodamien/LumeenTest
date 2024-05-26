package com.lumeen.domain.model

import java.util.Locale


sealed class Joke {

    data class Single(
        override val id: Long,
        val joke: String,
        override val lang: Locale,
    ): Joke()

    data class TwoParts(
        override val id: Long,
        val setup: String,
        val delivery: String,
        override val lang: Locale,
    ): Joke()

    abstract val id: Long
    abstract val lang: Locale
}
