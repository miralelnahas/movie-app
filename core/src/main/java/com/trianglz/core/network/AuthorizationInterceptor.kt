package com.trianglz.core.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request().newBuilder()
        //TODO: move token to local.properties
        request = request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZTczZjA5MGMyNjg1MTkyMGI1MTIyMzE3Y2JiODFlOSIsInN1YiI6IjY1ZTEwYzg2YTM5ZDBiMDE2MzA3ZGMxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lyeQanCALInVMeUm_vXHtQDyi-epNOZ1N8nUAYI24Sc")
        return chain.proceed(request.build())
    }
}