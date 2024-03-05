package com.trianglz.ui.views.movies

import com.trianglz.data.models.movies.Movie

sealed class MoviesEvent {
    class MovieClick(val movie: Movie) : MoviesEvent()
}