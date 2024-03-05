package com.trianglz.core.datasources

import com.trianglz.core.network.movies.response.movieDetails.MovieDetailsResponse
import com.trianglz.core.network.movies.response.movies.MovieResponse
import com.trianglz.data.models.movieDetails.MovieDetails
import com.trianglz.data.models.movies.Movie

object Mapper {
    fun MovieResponse.toMovie(): Movie =
        Movie(id, imageUrl?:"", String.format("%.1f", voteAverage))

    fun MovieDetailsResponse.toMovieDetails(): MovieDetails =
        MovieDetails(
            id,
            originalLanguage ?: "",
            originalTitle ?: "",
            overview ?: "",
            releaseDate ?: "",
            posterPath ?: "",
            voteAverage ?: 0.0,
            voteCount ?: 0
        )

}