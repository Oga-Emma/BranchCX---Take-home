package app.seven.branchcx.core.utils

import android.content.Context
import app.seven.branchcx.core.service.cache.LocalCache
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor (private val cache: LocalCache) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        val token = cache.getAuthToken()
        if(token.isNotBlank()){
            requestBuilder.addHeader("X-Branch-Auth-Token", token)
        }

        return chain.proceed(requestBuilder.build())
    }
}