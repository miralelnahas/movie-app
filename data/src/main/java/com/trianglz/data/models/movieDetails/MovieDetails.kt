package com.trianglz.data.models.movieDetails

data class MovieDetails(
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int
)