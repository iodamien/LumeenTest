package com.lumeen.utils

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
