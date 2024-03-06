package com.trianglz.core.datasources

import com.trianglz.core.database.movies.MovieEntity
import com.trianglz.core.network.movies.response.movieDetails.MovieDetailsResponse
import com.trianglz.core.network.movies.response.movies.MovieResponse
import com.trianglz.data.models.movieDetails.MovieDetails
import com.trianglz.data.models.movies.Movie

object Mapper {
    fun MovieResponse.toMovie(): Movie =
        Movie(id, title, imageUrl ?: "", String.format("%.1f", voteAverage))

    fun MovieEntity.toMovie(): Movie =
        Movie(id, imageUrl, String.format("%.1f", voteAverage))

    //TODO: fix values
    fun MovieResponse.toMovieEntity(): MovieEntity =
        MovieEntity(
            movieId = id,
            title = originalTitle,
            imageUrl = imageUrl ?: "",
            releaseDate = "12345",
            voteAverage = voteAverage
        )

    fun MovieDetailsResponse.toMovieDetails(): MovieDetails =
        MovieDetails(
            id,
            originalTitle ?: "",
            overview ?: "",
            releaseDate ?: "",
            posterPath ?: "",
            String.format("%.1f", voteAverage),
            runtime ?: 0,
            genres.map { it.name ?: "" },
            productionCountries.map { it.name ?: "" },
            productionCompanies.map { it.name ?: "" },
            spokenLanguages.map { it.englishName ?: "" },
        )

}