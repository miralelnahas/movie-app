package com.trianglz.core.network.movies

import com.trianglz.core.network.movies.response.movieDetails.MovieDetailsResponse
import com.trianglz.core.network.movies.response.movies.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Response<MoviesResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("query") searchQuery: String
    ): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<MovieDetailsResponse>

}