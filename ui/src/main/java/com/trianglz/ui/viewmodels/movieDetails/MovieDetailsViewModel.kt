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
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel() {
    private val _movieDetails: MutableStateFlow<MovieDetails?> =
        MutableStateFlow(null)

    val posterUrl = _movieDetails.map {
        it?.posterPath ?: ""
    }.toStateFlow("")

    val movieTitle = _movieDetails.map {
        it?.originalTitle ?: ""
    }.toStateFlow("")
    val overview = _movieDetails.map {
        it?.overview ?: ""
    }.toStateFlow("")
    val releaseDate = _movieDetails.map {
        it?.releaseDate?.let { releaseDate ->
            LocalDate.parse(releaseDate).year.toString()
        } ?: ""
    }.toStateFlow("")
    val voteAverage = _movieDetails.map {
        it?.voteAverage
    }.toStateFlow("")
    val duration = _movieDetails.map {
        it?.duration?.let { duration ->
            val hours = duration / 60
            val minutes = duration % 60
            val hourString = if (hours > 0) "${hours}h " else ""
            val minuteString = "${minutes}m"
            "$hourString $minuteString"
        } ?: ""
    }.toStateFlow("")
    val genres = _movieDetails.map {
        it?.genres?.joinToString(", ") ?: ""
    }.toStateFlow("")
    val productionCompanies = _movieDetails.map {
        it?.productionCompanies?.joinToString(", ") ?: ""
    }.toStateFlow("")
    val productionCountries = _movieDetails.map {
        it?.productionCountries?.joinToString(", ") ?: ""
    }.toStateFlow("")
    val spokenLanguages = _movieDetails.map {
        it?.spokenLanguages?.joinToString(", ") ?: ""
    }.toStateFlow("")


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