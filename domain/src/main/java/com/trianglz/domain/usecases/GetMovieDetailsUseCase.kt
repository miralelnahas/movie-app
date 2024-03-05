package com.trianglz.domain.usecases

import com.trianglz.data.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(id: Int) = moviesRepository.getMovieDetails(id)
}