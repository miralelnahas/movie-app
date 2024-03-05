package com.trianglz.domain.usecases

import com.trianglz.data.repositories.MoviesRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(searchQuery: String) = moviesRepository.searchMovies(searchQuery)
}