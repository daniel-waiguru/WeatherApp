package com.danielwaiguru.weatherapp.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.danielwaiguru.weatherapp.data.BuildConfig
import com.danielwaiguru.weatherapp.data.sources.remote.api.WeatherAppApiService
import com.danielwaiguru.weatherapp.data.sources.remote.interceptors.AppIdInterceptor
import com.danielwaiguru.weatherapp.data.sources.remote.interceptors.HeadersInterceptor
import com.danielwaiguru.weatherapp.data.sources.remote.utils.ApiEndpoints.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkingModule {
    private const val REQUEST_TIMEOUT_S = 20L

    @[
        Singleton
        Provides
    ]
    internal fun provideHttpClient(
        @ApplicationContext appContext: Context
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        val chuckerInterceptor = ChuckerInterceptor.Builder(appContext)
            .collector(ChuckerCollector(appContext))
            .maxContentLength(250_000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(true)
            .build()
        return OkHttpClient.Builder()
            .readTimeout(REQUEST_TIMEOUT_S, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIMEOUT_S, TimeUnit.SECONDS)
            .addInterceptor(AppIdInterceptor())
            .addInterceptor(HeadersInterceptor())
            .addInterceptor(logging)
            .addInterceptor(chuckerInterceptor)
            .dispatcher(
                Dispatcher().apply {
                    maxRequestsPerHost = 8
                }
            )
            .build()
    }

    @[
        Singleton
        Provides
    ]
    internal fun provideConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create().apply {
            asLenient()
            failOnUnknown()
            withNullSerialization()
        }

    @[
        Singleton
        Provides
    ]
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @[
        Singleton
        Provides
    ]
    internal fun apiServiceBuilder(retrofit: Retrofit): WeatherAppApiService {
        return retrofit.create(WeatherAppApiService::class.java)
    }
}
