package com.danielwaiguru.weatherapp.data.sources.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AppIdInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("appid", "bb90efd61c3112b077246f518207d544")
            .build()
        return chain.proceed(
            request.newBuilder().url(url).build()
        )
    }
}
