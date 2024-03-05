package com.trianglz.ui.viewmodels.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.trianglz.data.models.movieDetails.MovieDetails
import com.trianglz.domain.usecases.GetMovieDetailsUseCase
import com.trianglz.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel() {
    private val _movieDetails: MutableStateFlow<MovieDetails?> =
        MutableStateFlow(null)
//    val movieDetails: StateFlow<MovieDetails?> = _movieDetails

    val posterUrl = _movieDetails.map {
        it?.posterPath ?: ""
    }.toStateFlow("")

    val movieTitle = _movieDetails.map {
        it?.originalTitle ?: ""
    }.toStateFlow("")

    val originalLanguage = _movieDetails.map {
        it?.originalLanguage ?: ""
    }.toStateFlow("")
    val overview = _movieDetails.map {
        it?.overview ?: ""
    }.toStateFlow("")
    val releaseDate = _movieDetails.map {
        it?.releaseDate ?: ""
    }.toStateFlow("")
    val voteAverage = _movieDetails.map {
        it?.voteAverage ?: 0.0
    }.toStateFlow(0.0)
    val voteCount = _movieDetails.map {
        it?.voteCount ?: 0
    }.toStateFlow(0)


    init {
        savedStateHandle.get<Int>(ARG_ID)?.let {
            viewModelScope.launch {
                getMovieDetailsUseCase(it).apply {
                    onSuccess {
                        _movieDetails.value = it
                    }
                    onFailure {

                    }
                }
            }
        }
    }

    companion object {
        const val ARG_ID = "id"
    }
}