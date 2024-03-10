package com.trianglz.ui.viewmodels.movies

import androidx.lifecycle.viewModelScope
import com.trianglz.data.models.movies.Movie
import com.trianglz.data.models.movies.SortType
import com.trianglz.domain.usecases.GetMoviesUseCase
import com.trianglz.domain.usecases.IsNetworkConnectedUseCase
import com.trianglz.domain.usecases.SearchMoviesUseCase
import com.trianglz.ui.base.BasePagingViewModel
import com.trianglz.ui.viewmodels.search.SearchUiModel
import com.trianglz.ui.views.movies.MovieIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    searchMoviesUseCase: SearchMoviesUseCase,
    isNetworkConnectedUseCase: IsNetworkConnectedUseCase
) : BasePagingViewModel<MovieIntent, Movie>(isNetworkConnectedUseCase) {

    private var _sortType: SortType = SortType.MOST_POPULAR

    val searchUiModel = SearchUiModel(viewModelScope, searchMoviesUseCase)

    override fun onTriggerEvent(eventType: MovieIntent) {
        when (eventType) {
            is MovieIntent.LoadMovies -> loadMovies()
            is MovieIntent.ChangeSortType -> changeSortType(eventType.sortType)
        }
    }

    private fun loadMovies() {
        launchPagingRequest {
            getMoviesUseCase(_sortType).data
        }
    }

    private fun changeSortType(sortType: SortType) {
        _sortType = sortType
        loadMovies()
    }
}