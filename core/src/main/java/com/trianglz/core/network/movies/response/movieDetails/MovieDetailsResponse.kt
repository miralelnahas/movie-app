package com.trianglz.core.network.movies.response.movieDetails

import com.google.gson.annotations.SerializedName


data class MovieDetailsResponse(
    @SerializedName("genres") var genres: ArrayList<Genres> = arrayListOf(),
    @SerializedName("id") var id: Int,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("production_companies") var productionCompanies: ArrayList<ProductionCompanies> = arrayListOf(),
    @SerializedName("production_countries") var productionCountries: ArrayList<ProductionCountries> = arrayListOf(),
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("runtime") var runtime: Int? = null,
    @SerializedName("spoken_languages") var spokenLanguages: ArrayList<SpokenLanguages> = arrayListOf(),
    @SerializedName("vote_average") var voteAverage: Double? = null

)