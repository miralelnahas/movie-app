package com.trianglz.ui.viewmodels.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.trianglz.data.models.movies.Movie
import com.trianglz.domain.usecases.SearchMoviesUseCase
import com.trianglz.ui.base.BaseViewModel
import com.trianglz.ui.utils.ItemClick
import com.trianglz.ui.viewmodels.movies.MovieUiModel
import com.trianglz.ui.views.search.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchMoviesUseCase: SearchMoviesUseCase) :
    BaseViewModel(), ItemClick<Movie> {

    private val _event = Channel<SearchEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    fun getSearchQuery() = _searchQuery.value
    fun setSearchQuery(searchQuery: String) {
        _searchQuery.value = searchQuery
    }

    var movies: StateFlow<PagingData<Movie>> = _searchQuery
        .debounce(300)
        .flatMapLatest {
            searchMoviesUseCase(it).data.cachedIn(viewModelScope)
        }.toStateFlow(PagingData.empty())

    fun getMovieUiModel(movie: Movie): MovieUiModel = MovieUiModel(movie, this)

    override fun onItemClick(t: Movie) {
        sendEvent(_event, SearchEvent.MovieClick(t))
    }
}