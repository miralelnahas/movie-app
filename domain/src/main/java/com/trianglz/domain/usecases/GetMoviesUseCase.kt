package com.trianglz.domain.usecases

import com.trianglz.data.models.movies.SortType
import com.trianglz.data.repositories.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(sortType: SortType) = moviesRepository.getMovies(sortType)
}