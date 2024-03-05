package com.trianglz.ui.viewmodels.movies

import com.trianglz.data.models.movies.Movie
import com.trianglz.ui.utils.ItemClick

class MovieUiModel(val movie: Movie, private val itemClick: ItemClick<Movie>) {
    fun onMovieClick() {
        itemClick.onItemClick(movie)
    }
}