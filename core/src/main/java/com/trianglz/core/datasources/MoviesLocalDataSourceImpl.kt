package com.trianglz.core.datasources

import com.trianglz.core.database.AppDatabase
import com.trianglz.core.database.movies.MoviesDao
import com.trianglz.core.database.remoteKeys.RemoteKeyEntity
import com.trianglz.core.database.remoteKeys.RemoteKeysDao
import com.trianglz.core.datasources.Mapper.toMovieEntity
import com.trianglz.core.network.movies.response.movies.MovieResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(
    val database: AppDatabase,
    private val moviesDao: MoviesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val dispatcher: CoroutineDispatcher
) {

    fun getMovies() = moviesDao.getMovies()

    suspend fun addMovies(movies: List<MovieResponse>) {
        movies.forEach {
            moviesDao.apply {
                insert(it.toMovieEntity())
            }
        }
    }

    suspend fun clearAll() {
        moviesDao.clearAll()
    }

    suspend fun clearRemoteKeys() {
        remoteKeysDao.clearRemoteKeys()
    }

    suspend fun insertAllKeys(keys: List<RemoteKeyEntity>) {
        keys.forEach {
            remoteKeysDao.insert(it)
        }
    }

    suspend fun remoteKeysRepoId(id: Int) =
        remoteKeysDao.remoteKeysRepoId(id)

}