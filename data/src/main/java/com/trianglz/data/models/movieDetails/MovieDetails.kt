package com.trianglz.data.models.movieDetails

data class MovieDetails(
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val voteAverage: String,
    val duration: Int,
    val genres: List<String>,
    val productionCountries: List<String>,
    val productionCompanies: List<String>,
    val spokenLanguages: List<String>,
)