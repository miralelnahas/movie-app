package com.trianglz.core.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.trianglz.core.datasources.Mapper.toMovie
import com.trianglz.core.datasources.Mapper.toMovieDetails
import com.trianglz.core.network.RetrofitClientExt.apiCall
import com.trianglz.core.network.base.BasePagingSource
import com.trianglz.core.network.movies.MoviesApi
import com.trianglz.core.network.movies.response.movies.MoviesMediator
import com.trianglz.core.network.movies.response.movies.MoviesPagingSource
import com.trianglz.data.models.movieDetails.MovieDetails
import com.trianglz.data.models.movies.Movie
import com.trianglz.data.models.movies.PagingResponse
import com.trianglz.data.models.movies.SortType
import com.trianglz.data.repositories.MoviesDataSource
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import javax.inject.Inject

class MoviesDataSourceImpl @Inject constructor(
    retrofit: Retrofit,
    private val moviesLocalDataSourceImpl: MoviesLocalDataSourceImpl
) : MoviesDataSource {
    private val moviesApi = retrofit.create(MoviesApi::class.java)

    override suspend fun getMovies(sortType: SortType): PagingResponse<PagingData<Movie>> {
        val pagingSource = MoviesPagingSource(moviesApi, sortType = sortType)
        return fetchMovies(pagingSource)
    }

    override suspend fun searchMovies(searchQuery: String): PagingResponse<PagingData<Movie>> {
        val pagingSource = MoviesPagingSource(moviesApi, searchQuery = searchQuery)
        return fetchMovies(pagingSource)
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun fetchMovies(pagingSource: MoviesPagingSource): PagingResponse<PagingData<Movie>> {
        return PagingResponse(
            pagingSource.itemCount, Pager(
                PagingConfig(
                    pageSize = BasePagingSource.LIST_PAGE_SIZE,
                    enablePlaceholders = true,
                    initialLoadSize = BasePagingSource.LIST_PAGE_SIZE
                ),
                remoteMediator = MoviesMediator(moviesApi, moviesLocalDataSourceImpl)
            ) {
                //TODO: get movies according to sort type, search query
                moviesLocalDataSourceImpl.getMovies()
            }.flow.map {
                it.map { movieEntity ->
                    movieEntity.toMovie()
                }
            }
        )
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetails> {
        return apiCall {
            moviesApi.getMovieDetails(id)
        }.map {
            it.toMovieDetails()
        }
    }
}