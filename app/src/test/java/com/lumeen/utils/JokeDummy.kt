package com.lumeen.utils

import com.lumeen.data.database.entity.JokeEntity
import com.lumeen.data.network.data.JokeData
import com.lumeen.domain.model.Joke
import java.util.Locale

val simpleJokeDummy = Joke.Single(
    id = 1234L,
    joke = "joke",
)

val twoPartsJokeDummy = Joke.TwoParts(
    id = 987L,
    setup = "setup",
    delivery = "delivery",
)

val simpleJokeDataDummy = JokeData.Single(
    id = 1234L,
    joke = "joke",
    lang = "en",
)

val simpleJokeEntityDummy = JokeEntity(
    id = 1234L,
    joke = "joke",
    type = "single",
    setup = null,
    delivery = null,
)

val twoPartsJokeEntityDummy = JokeEntity(
    id = 987L,
    joke = null,
    type = "twopart",
    setup = "setup",
    delivery = "delivery",
)
