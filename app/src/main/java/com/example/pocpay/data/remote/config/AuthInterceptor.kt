package com.example.pocpay.data.remote.config

import com.example.pocpay.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url = req.url.newBuilder().addQueryParameter("app_id", BuildConfig.API_KEY).build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}