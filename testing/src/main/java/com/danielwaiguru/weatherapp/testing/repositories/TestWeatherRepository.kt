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

package com.danielwaiguru.weatherapp.testing.repositories

import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast
import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import com.danielwaiguru.weatherapp.testing.testData.testForecast
import com.danielwaiguru.weatherapp.testing.testData.testWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestWeatherRepository : WeatherRepository {
    private val weatherResult = MutableStateFlow<ResultWrapper<Weather?>>(
        ResultWrapper.Success(
            testWeather(),
        ),
    )

    private val forecastResult = MutableStateFlow<ResultWrapper<List<WeatherForecast>>>(
        ResultWrapper.Success(
            listOf(
                testForecast(),
                testForecast(
                    id = 4,
                    date = 1704803010,
                    conditionId = 200,
                ),
            ),
        ),
    )
    override suspend fun getCurrentWeather(userLocation: UserLocation): Flow<ResultWrapper<Weather?>> {
        return weatherResult
    }

    override suspend fun getWeatherForecast(userLocation: UserLocation): Flow<ResultWrapper<List<WeatherForecast>>> {
        return forecastResult
    }

    fun setWeatherResult(result: ResultWrapper<Weather?>) {
        weatherResult.update { result }
    }

    fun setForecastResult(result: ResultWrapper<List<WeatherForecast>>) {
        forecastResult.update { result }
    }
}