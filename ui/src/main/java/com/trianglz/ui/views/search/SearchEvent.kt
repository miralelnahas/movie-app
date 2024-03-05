package com.trianglz.ui.views.search

import com.trianglz.data.models.movies.Movie

sealed class SearchEvent {
    class MovieClick(val movie: Movie) : SearchEvent()
}