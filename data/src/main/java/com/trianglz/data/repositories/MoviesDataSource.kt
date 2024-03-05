package com.trianglz.data.repositories

import androidx.paging.PagingData
import com.trianglz.data.models.movieDetails.MovieDetails
import com.trianglz.data.models.movies.Movie
import com.trianglz.data.models.movies.PagingResponse
import com.trianglz.data.models.movies.SortType

interface MoviesDataSource {
    suspend fun getMovies(sortType: SortType): PagingResponse<PagingData<Movie>>
    suspend fun searchMovies(searchQuery: String): PagingResponse<PagingData<Movie>>
    suspend fun getMovieDetails(id: Int): Result<MovieDetails>
}