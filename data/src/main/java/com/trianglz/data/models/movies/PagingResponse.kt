package com.trianglz.data.models.movies

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

data class PagingResponse<T>(
    val count: StateFlow<Int>,
    val data: Flow<T>,
)