package com.trianglz.core.database.movies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movieentity ORDER BY id")
    fun getMovies(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insert(movieEntity: MovieEntity): Long

    @Query("DELETE FROM movieentity")
    suspend fun clearAll()
}