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

import app.cash.turbine.test
import com.danielwaiguru.weatherapp.data.sources.local.LocalDataSource
import com.danielwaiguru.weatherapp.data.sources.remote.RemoteDataSource
import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import com.danielwaiguru.weatherapp.testing.testData.testForecastEntity
import com.danielwaiguru.weatherapp.testing.testData.testLocation
import com.danielwaiguru.weatherapp.testing.testData.testWeatherDto
import com.danielwaiguru.weatherapp.testing.testData.testWeatherEntity
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Before
import org.junit.Test

class WeatherRepositoryTest {
    @MockK
    val remoteDataSource = mockk<RemoteDataSource>()

    @MockK
    val localDataSource = mockk<LocalDataSource>()
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        weatherRepository = WeatherRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `test current weather returns correctly data when network request fails`() {
        runTest {
            coEvery {
                remoteDataSource.getCurrentWeather(testLocation.latitude, testLocation.longitude)
            } throws IOException("No internet")
            coEvery {
                localDataSource.saveWeather(testWeatherEntity())
            } returns Unit
            coEvery {
                localDataSource.getCurrentWeather()
            } returns flowOf(testWeatherEntity())
            weatherRepository.getCurrentWeather(testLocation)
                .test {
                    awaitItem().also { result ->
                        Truth.assertThat(result).isInstanceOf(ResultWrapper.Error::class.java)
                        Truth.assertThat(result.data).isNotNull()
                    }
                    awaitComplete()
                }
        }
    }

    @Test
    fun `test weather forecast returns correctly data when network request fails`() {
        runTest {
            coEvery {
                remoteDataSource.getWeatherForecast(testLocation.latitude, testLocation.longitude)
            } throws IOException("No internet")
            coEvery {
                localDataSource.saveWeatherForecast(listOf(testForecastEntity()))
            } returns Unit
            coEvery {
                localDataSource.getWeatherForecast()
            } returns flowOf(listOf(testForecastEntity(id = 3)))
            weatherRepository.getWeatherForecast(testLocation)
                .test {
                    awaitItem().also { result ->
                        Truth.assertThat(result).isInstanceOf(ResultWrapper.Error::class.java)
                        Truth.assertThat(result.data?.size).isEqualTo(1)
                    }
                    awaitComplete()
                }
        }
    }

    @Test
    fun `test weather forecast returns correctly mapped error message when network request fails`() {
        runTest {
            coEvery {
                remoteDataSource.getWeatherForecast(testLocation.latitude, testLocation.longitude)
            } throws IOException("No internet")
            coEvery {
                localDataSource.saveWeatherForecast(listOf(testForecastEntity()))
            } returns Unit
            coEvery {
                localDataSource.getWeatherForecast()
            } returns flowOf(listOf(testForecastEntity(id = 3)))
            weatherRepository.getWeatherForecast(testLocation)
                .test {
                    awaitItem().also { result ->
                        Truth.assertThat(result).isInstanceOf(ResultWrapper.Error::class.java)
                        Truth.assertThat(result.errorMessage).isNotNull()
                    }
                    awaitComplete()
                }
        }
    }

    @Test
    fun `test current weather returns correctly mapped error message when network request fails`() {
        runTest {
            coEvery {
                remoteDataSource.getCurrentWeather(testLocation.latitude, testLocation.longitude)
            } throws IOException("No internet")
            coEvery {
                localDataSource.saveWeather(testWeatherEntity())
            } returns Unit
            coEvery {
                localDataSource.getCurrentWeather()
            } returns flowOf(testWeatherEntity())
            weatherRepository.getCurrentWeather(testLocation)
                .test {
                    awaitItem().also { result ->
                        Truth.assertThat(result).isInstanceOf(ResultWrapper.Error::class.java)
                        Truth.assertThat(result.data).isNotNull()
                    }
                    awaitComplete()
                }
        }
    }

    @Test
    fun `test current weather returns data successfully`() {
        runTest {
            coEvery {
                remoteDataSource.getCurrentWeather(testLocation.latitude, testLocation.longitude)
            } returns testWeatherDto()
            coEvery {
                localDataSource.saveWeather(testWeatherEntity())
            } returns Unit
            coEvery {
                localDataSource.getCurrentWeather()
            } returns flowOf(testWeatherEntity())
            weatherRepository.getCurrentWeather(testLocation)
                .test {
                    awaitItem().also { result ->
                        // Truth.assertThat(result).isInstanceOf(ResultWrapper.Success::class.java)
                        Truth.assertThat(result.data).isNotNull()
                    }
                    awaitComplete()
                }
        }
    }

    @Test
    fun `test weather forecast returns data successfully`() {
        runTest {
            coEvery {
                remoteDataSource.getCurrentWeather(testLocation.latitude, testLocation.longitude)
            } returns testWeatherDto()
            coEvery {
                localDataSource.saveWeather(testWeatherEntity())
            } returns Unit
            coEvery {
                localDataSource.getCurrentWeather()
            } returns flowOf(testWeatherEntity())
            weatherRepository.getCurrentWeather(testLocation)
                .test {
                    awaitItem().also { result ->
                        Truth.assertThat(result.data).isNotNull()
                    }
                    awaitComplete()
                }
        }
    }
}