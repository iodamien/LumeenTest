package com.lumeen.data.network.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.lumeen.data.mapper.JOKE_SINGLE
import com.lumeen.data.mapper.JOKE_TWO_PARTS
import com.lumeen.data.network.data.JokeData
import java.lang.reflect.Type

internal class JokeDataSerializer: JsonDeserializer<JokeData> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): JokeData {
        val jsonObject = json?.asJsonObject ?: throw JsonParseException("Invalid JSON: $json")
        return when (val type = jsonObject.get("type").asString) {
            JOKE_SINGLE -> context?.deserialize(jsonObject, JokeData.Single::class.java)!!
            JOKE_TWO_PARTS -> context?.deserialize(jsonObject, JokeData.TwoPart::class.java)!!
            else -> throw JsonParseException("Unknown type: $type")
        }
    }
}
