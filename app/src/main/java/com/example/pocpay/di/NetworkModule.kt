package com.example.pocpay.di

import android.content.Context
import com.example.pocpay.BuildConfig
import com.example.pocpay.data.remote.api.CurrencyExchangeApi
import com.example.pocpay.data.remote.config.AuthInterceptor
import com.example.pocpay.data.remote.config.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl() = BuildConfig.BASE_API_URL

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BASE_URL") baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        val httpBuilder = OkHttpClient()
            .newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(networkInterceptor)
            .addInterceptor(authInterceptor)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            httpBuilder.addInterceptor(loggingInterceptor)
        }

        return httpBuilder.build()
    }

    @Provides
    fun provideAuthInterceptor() = AuthInterceptor()

    @Singleton
    @Provides
    fun provideNetworkInterceptor(
        @ApplicationContext appContext: Context
    ) = NetworkInterceptor(appContext)

    // APIs

    @Provides
    fun provideExchangeApi(retrofit: Retrofit): CurrencyExchangeApi = retrofit.create(CurrencyExchangeApi::class.java)
}