package com.trianglz.ui.views.movies

import com.trianglz.ui.base.BaseIntent

sealed class SearchIntent : BaseIntent() {
    class UpdateSearchQuery(val searchQuery: String) : SearchIntent()
    data object SearchMovies : SearchIntent()
}