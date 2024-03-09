package com.trianglz.ui.viewmodels.search

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.trianglz.data.DataState
import com.trianglz.data.models.movies.Movie
import com.trianglz.domain.usecases.SearchMoviesUseCase
import com.trianglz.ui.base.BaseUiModel
import com.trianglz.ui.views.movies.SearchIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch


class SearchUiModel(
    private val uiModelScope: CoroutineScope,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : BaseUiModel<PagingData<Movie>, SearchIntent>(uiModelScope) {

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    override fun onTriggerEvent(event: SearchIntent) {
        when (event) {
            is SearchIntent.UpdateSearchQuery -> updateSearchQuery(event.searchQuery)
            is SearchIntent.SearchMovies -> searchMovies()
        }
    }

    private fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun searchMovies() {
        uiModelScope.launch {
            _searchQuery
                .debounce(300)
                .collectLatest {
                    searchMoviesUseCase(it).data.cachedIn(uiModelScope)
                        .collectLatest { pagingData ->
                            _dataState.value = DataState.Success(pagingData)
                        }
                }
        }
    }
}