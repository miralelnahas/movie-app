package com.trianglz.ui.viewmodels.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.trianglz.data.models.movies.Movie
import com.trianglz.data.models.movies.SortType
import com.trianglz.domain.usecases.GetMoviesUseCase
import com.trianglz.ui.base.BaseViewModel
import com.trianglz.ui.utils.ItemClick
import com.trianglz.ui.views.movies.MoviesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    BaseViewModel(), ItemClick<Movie> {

    private val _event = Channel<MoviesEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    private val _sortType: MutableStateFlow<SortType> = MutableStateFlow(SortType.MOST_POPULAR)
    val sortType: StateFlow<SortType> = _sortType.asStateFlow()

    val movies : StateFlow<PagingData<Movie>> = _sortType.flatMapLatest {
        getMoviesUseCase(it).data
    }.cachedIn(viewModelScope).toStateFlow(PagingData.empty())


    init {
//        fetchMovies()
    }

//    private fun fetchMovies() {
//        viewModelScope.launch {
//            getMoviesUseCase(_sortType.value).apply {
//                movies = data.cachedIn(viewModelScope).toStateFlow(PagingData.empty())
//            }
//        }
//    }

    fun getMovieUiModel(movie: Movie) =
        MovieUiModel(movie, this)

    fun changeSortType(sortType: SortType) {
        _sortType.value = sortType
//        fetchMovies()
    }

    override fun onItemClick(t: Movie) {
        sendEvent(_event, MoviesEvent.MovieClick(t))
    }
}