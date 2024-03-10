package com.trianglz.data

sealed class DataState<out T> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val message: String) : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
    data object Empty : DataState<Nothing>()

    fun toData(): T? = if(this is Success) data else null
}