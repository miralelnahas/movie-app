package com.trianglz.ui.viewmodels.movieDetails

import androidx.lifecycle.SavedStateHandle
import com.trianglz.data.models.movieDetails.MovieDetails
import com.trianglz.domain.usecases.GetMovieDetailsUseCase
import com.trianglz.domain.usecases.IsNetworkConnectedUseCase
import com.trianglz.ui.base.BaseViewModel
import com.trianglz.ui.views.movieDetails.MovieDetailsIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    isNetworkConnectedUseCase: IsNetworkConnectedUseCase
) : BaseViewModel<MovieDetailsIntent, MovieDetails>(isNetworkConnectedUseCase) {

    private val movieId: Int? = savedStateHandle[ARG_ID]

    init {
        movieId?.let {
            launchRequest { getMovieDetailsUseCase(it) }
        }
    }

    companion object {
        const val ARG_ID = "id"
    }

    override fun onTriggerEvent(eventType: MovieDetailsIntent) {
        // There are no events in movie details
    }
}