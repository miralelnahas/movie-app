package com.trianglz.core.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object RetrofitClientExt {
    suspend fun <T : Any> apiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        call: suspend () -> Response<T>
    ): Result<T> {
        return withContext(dispatcher) {
            val response: Response<T>
            try {
                response = call.invoke()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(Exception())
            }
        }
    }
}