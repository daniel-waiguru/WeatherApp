/*
 * MIT License
 *
 * Copyright (c) 2024 Daniel Waiguru
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
import kotlinx.serialization.json.Json
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

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

    @Provides
    @Singleton
    internal fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @[
    Singleton
    Provides
    ]
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
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
