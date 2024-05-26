package com.lumeen.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lumeen.data.database.dao.JokeDao
import com.lumeen.data.database.entity.JokeEntity

internal const val DATABASE_VERSION_CURRENT = 1

@Database(
    entities = [
        JokeEntity::class
    ],
    version = DATABASE_VERSION_CURRENT,
)
internal abstract class JokeDatabase: RoomDatabase() {

    abstract fun jokeDao(): JokeDao

    companion object {
        operator fun invoke(
            context: Context,
            databaseName: String,
        ): JokeDatabase = Room.databaseBuilder(context, JokeDatabase::class.java, databaseName)
            .build()
    }
}
