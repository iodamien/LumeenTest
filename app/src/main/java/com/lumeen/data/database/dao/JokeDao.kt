package com.lumeen.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lumeen.data.database.entity.JokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface JokeDao {

    @Query(
        """
        SELECT *
        FROM joke
    """
    )
    fun getAll(): Flow<List<JokeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(joke: JokeEntity)

    @Query("""
        DELETE FROM JOKE
        WHERE id = :jokeId
    """)
    suspend fun deleteById(jokeId: Long)

    @Query("""
        SELECT *
        FROM joke
        WHERE id = :jokeId
        LIMIT 1
    """)
    fun getById(jokeId: Long): Flow<JokeEntity?>
}
