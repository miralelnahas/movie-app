package com.trianglz.core.network.movies.response.movies

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("id")
    val id : Int,
    @SerializedName("poster_path")
    val imageUrl: String?,
    @SerializedName("vote_average")
    val voteAverage: Double
)