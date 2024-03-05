package com.trianglz.core.network.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BasePagingSource<T : Any> : PagingSource<Int, T>() {

    protected var response: List<T> = listOf()

    protected val _itemCount = MutableStateFlow(0)
    val itemCount: StateFlow<Int> = _itemCount

    abstract override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T>

    override fun getRefreshKey(state: PagingState<Int, T>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIST_PAGE_SIZE) ?: anchorPage?.nextKey?.minus(LIST_PAGE_SIZE)
        }

    protected fun getPrevCursor(pageCursor: Int) =
        if (pageCursor == LIST_STARTING_INDEX) null else pageCursor

    protected fun getNextCursor(pageCursor: Int, loadSize: Int) =
        if (response.size < loadSize || response.isEmpty()) null else pageCursor + 1


    companion object {
        const val LIST_PAGE_SIZE = 20
        const val LIST_STARTING_INDEX = 1
    }
}