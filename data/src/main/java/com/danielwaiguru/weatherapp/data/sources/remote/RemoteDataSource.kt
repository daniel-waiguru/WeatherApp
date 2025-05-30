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

package com.danielwaiguru.weatherapp.data.sources.remote

import com.danielwaiguru.weatherapp.data.models.responses.ForecastResponse
import com.danielwaiguru.weatherapp.data.models.responses.WeatherResponse
import com.danielwaiguru.weatherapp.data.sources.remote.api.WeatherAppApiService
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): WeatherResponse

    suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): ForecastResponse
}

internal class RetrofitDataSource @Inject constructor(
    private val apiService: WeatherAppApiService
) : RemoteDataSource {
    override suspend fun getCurrentWeather(latitude: Double, longitude: Double): WeatherResponse =
        apiService.getCurrentWeather(latitude, longitude)

    override suspend fun getWeatherForecast(latitude: Double, longitude: Double): ForecastResponse =
        apiService.getWeatherForecast(latitude, longitude)
}
