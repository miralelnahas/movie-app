package com.trianglz.core.network.movies.response.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.trianglz.core.database.movies.MovieEntity
import com.trianglz.core.database.remoteKeys.RemoteKeyEntity
import com.trianglz.core.datasources.MoviesLocalDataSourceImpl
import com.trianglz.core.network.RetrofitClientExt.apiCall
import com.trianglz.core.network.movies.MoviesApi
import com.trianglz.data.models.movies.SortType

@ExperimentalPagingApi
class MoviesMediator(
    private val moviesApi: MoviesApi,
    private val moviesLocalDataSourceImpl: MoviesLocalDataSourceImpl,
    private val sortType: SortType = SortType.MOST_POPULAR,
    private val searchQuery: String = ""
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        var response: List<MovieResponse> = listOf()

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyEntityClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyEntityForLastItem(state)
                val nextKey = remoteKey?.nextKey
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                nextKey
            }
        }
        try {
            apiCall {
                println("Remote page: $page")
                if (searchQuery.isEmpty()) {
                    when (sortType) {
                        SortType.MOST_POPULAR -> moviesApi.getPopularMovies(page)
                        SortType.TOP_RATED -> moviesApi.getTopRatedMovies(page)
                    }
                } else moviesApi.searchMovies(page, searchQuery)
            }.map {
                response = it.results
            }

            val endOfPagination = response.isEmpty()

            if (loadType == LoadType.REFRESH) {
                moviesLocalDataSourceImpl.clearRemoteKeys()
                moviesLocalDataSourceImpl.clearAll()
                println("local clear")
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPagination) null else page + 1

            val keys = response.map {
                RemoteKeyEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            moviesLocalDataSourceImpl.insertAllKeys(keys)
            moviesLocalDataSourceImpl.addMovies(response)
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
//            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (ex: Exception) {
            return MediatorResult.Error(ex)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movieId ->
                moviesLocalDataSourceImpl.remoteKeysRepoId(movieId.movieId)
            }
    }

    private suspend fun getRemoteKeyEntityClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                moviesLocalDataSourceImpl.remoteKeysRepoId(movieId)
            }
        }
    }

    private suspend fun getRemoteKeyEntityForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                moviesLocalDataSourceImpl.remoteKeysRepoId(repo.movieId)
            }
    }

}