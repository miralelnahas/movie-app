package com.trianglz.core.network.movies.response.movies

import com.trianglz.core.datasources.Mapper.toMovie
import com.trianglz.core.network.RetrofitClientExt.apiCall
import com.trianglz.core.network.base.BasePagingSource
import com.trianglz.core.network.movies.MoviesApi
import com.trianglz.data.models.movies.Movie
import com.trianglz.data.models.movies.SortType

class MoviesPagingSource(
    private val moviesApi: MoviesApi,
    private val sortType: SortType = SortType.MOST_POPULAR,
    private val searchQuery: String = ""
) : BasePagingSource<Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageCursor = params.key ?: 1
        return try {
            apiCall {
                if (searchQuery.isEmpty()) {
                    when (sortType) {
                        SortType.MOST_POPULAR -> moviesApi.getPopularMovies(pageCursor)
                        SortType.TOP_RATED -> moviesApi.getTopRatedMovies(pageCursor)
                    }
                } else moviesApi.searchMovies(pageCursor, searchQuery)
            }.map {
                response = it.results.map {
                    it.toMovie()
                }
            }

            val prevCursor = getPrevCursor(pageCursor)
            val nextCursor = getNextCursor(pageCursor, params.loadSize)

            LoadResult.Page(data = response, prevKey = prevCursor, nextKey = nextCursor)

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}