package com.lumeen.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Locale

@Entity(
    tableName = "joke"
)
data class JokeEntity(
    @PrimaryKey val id: Long,
    val type: String,
    val joke: String?,
    val setup: String?,
    val delivery: String?,
)
