package com.trianglz.ui.views.movies

import com.trianglz.data.models.movies.SortType
import com.trianglz.ui.base.BaseIntent

sealed class MovieIntent : BaseIntent() {

    data object LoadMovies : MovieIntent()
    class ChangeSortType(var sortType: SortType) : MovieIntent()
}