package com.lumeen.data.mapper

import android.util.Log
import com.lumeen.data.database.entity.JokeEntity
import com.lumeen.data.network.data.JokeData
import com.lumeen.domain.model.Joke
import java.util.Locale

fun JokeEntity.toDomain(): Joke? {
    return when (type) {
        JOKE_SINGLE -> Joke.Single(
            id = id,
            joke = joke.orEmpty(),
        )
        JOKE_TWO_PARTS -> Joke.TwoParts(
            id = id,
            setup = setup.orEmpty(),
            delivery = delivery.orEmpty(),
        )
        else -> {
            Log.e("JokeMapper", "Not managed joke $id with type: $type")
            null
        }
    }
}

fun Joke.toEntity(): JokeEntity {
    return when (this) {
        is Joke.Single -> JokeEntity(
            id = id,
            joke = joke,
            setup = null,
            delivery = null,
            type = JOKE_SINGLE,
        )
        is Joke.TwoParts -> JokeEntity(
            id = id,
            joke = null,
            setup = setup,
            delivery = delivery,
            type = JOKE_TWO_PARTS,
        )
    }
}

fun JokeData.toDomain(): Joke {
    return when (this) {
        is JokeData.Single -> Joke.Single(
            id = id,
            joke = joke,
        )
        is JokeData.TwoPart -> Joke.TwoParts(
            id = id,
            setup = setup,
            delivery = delivery,
        )
    }
}

internal const val JOKE_SINGLE = "single"
internal const val JOKE_TWO_PARTS = "twopart"
