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

package com.danielwaiguru.weatherapp.data.repositories

import com.danielwaiguru.weatherapp.data.mappers.toForecastEntity
import com.danielwaiguru.weatherapp.data.mappers.toWeather
import com.danielwaiguru.weatherapp.data.mappers.toWeatherEntity
import com.danielwaiguru.weatherapp.data.mappers.toWeatherForecast
import com.danielwaiguru.weatherapp.data.models.entities.ForecastEntity
import com.danielwaiguru.weatherapp.data.sources.local.LocalDataSource
import com.danielwaiguru.weatherapp.data.sources.remote.RemoteDataSource
import com.danielwaiguru.weatherapp.data.utils.networkBoundResource
import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast
import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import com.danielwaiguru.weatherapp.domain.utils.Dispatcher
import com.danielwaiguru.weatherapp.domain.utils.DispatcherProvider
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class WeatherRepositoryImpl
    @Inject
    constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        @Dispatcher(DispatcherProvider.IO) private val ioDispatcher: CoroutineDispatcher,
    ) : WeatherRepository {
        override suspend fun getCurrentWeather(
            userLocation: UserLocation,
        ): Flow<ResultWrapper<Weather?>> =
            networkBoundResource(
                query = {
                    localDataSource.getCurrentWeather()
                        .map { weatherEntity -> weatherEntity?.toWeather() }
                },
                fetch = {
                    remoteDataSource.getCurrentWeather(
                        longitude = userLocation.longitude,
                        latitude = userLocation.latitude,
                    )
                },
                saveFetchResult = { weatherResponse ->
                    val currentWeatherEntity = weatherResponse.toWeatherEntity()
                    withContext(NonCancellable) {
                        localDataSource.saveWeather(currentWeatherEntity)
                    }
                },
            ).flowOn(ioDispatcher)

        override suspend fun getWeatherForecast(
            userLocation: UserLocation,
        ): Flow<ResultWrapper<List<WeatherForecast>>> =
            networkBoundResource(
                query = {
                    localDataSource.getWeatherForecast()
                        .map { entities -> entities.map(ForecastEntity::toWeatherForecast) }
                },
                fetch = {
                    remoteDataSource.getWeatherForecast(
                        latitude = userLocation.latitude,
                        longitude = userLocation.longitude,
                    )
                },
                saveFetchResult = { forecastResponse ->
                    val forecastEntity = forecastResponse.toForecastEntity()
                    withContext(NonCancellable) {
                        localDataSource.saveWeatherForecast(forecastEntity)
                    }
                },
            ).flowOn(ioDispatcher)
    }