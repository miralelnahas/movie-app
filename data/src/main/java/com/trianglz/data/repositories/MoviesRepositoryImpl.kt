package com.trianglz.data.repositories

import com.trianglz.data.models.movies.SortType
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val moviesDataSource: MoviesDataSource) :
    MoviesRepository {
    override suspend fun getMovies(sortType: SortType) = moviesDataSource.getMovies(sortType)

    override suspend fun searchMovies(searchQuery: String) = moviesDataSource.searchMovies(searchQuery)

    override suspend fun getMovieDetails(id: Int) = moviesDataSource.getMovieDetails(id)
}