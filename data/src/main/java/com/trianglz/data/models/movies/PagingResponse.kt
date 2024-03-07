package com.trianglz.data.models.movies

import kotlinx.coroutines.flow.Flow

data class PagingResponse<T>(
    val data: Flow<T>,
)