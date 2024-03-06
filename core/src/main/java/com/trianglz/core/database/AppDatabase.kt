package com.trianglz.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trianglz.core.database.movies.MovieEntity
import com.trianglz.core.database.movies.MoviesDao
import com.trianglz.core.database.remoteKeys.RemoteKeysDao
import com.trianglz.core.database.remoteKeys.RemoteKeyEntity

@Database(entities = [MovieEntity::class, RemoteKeyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieEntityDao(): MoviesDao
    abstract fun remoteKeyDao(): RemoteKeysDao
}