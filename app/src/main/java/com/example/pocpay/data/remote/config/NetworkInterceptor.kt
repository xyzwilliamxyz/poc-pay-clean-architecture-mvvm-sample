package com.example.pocpay.data.remote.config

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.pocpay.core.exception.NoInternetConnectionException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor constructor(private val appContext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isNetworkAvailable()){
            throw NoInternetConnectionException()
        }
        val newRequest = chain.request().newBuilder().build()
        return chain.proceed(newRequest)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}