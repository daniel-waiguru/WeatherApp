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

package com.danielwaiguru.weatherapp.presentation.weather

import com.danielwaiguru.weatherapp.domain.usecases.GetCurrentWeatherUseCase
import com.danielwaiguru.weatherapp.domain.usecases.GetWeatherForecastUseCase
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import com.danielwaiguru.weatherapp.testing.base.BaseViewModelTest
import com.danielwaiguru.weatherapp.testing.location.TestLocationService
import com.danielwaiguru.weatherapp.testing.repositories.TestWeatherRepository
import com.danielwaiguru.weatherapp.testing.testData.testWeather
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class WeatherViewModelTest : BaseViewModelTest() {
    private val weatherRepository = TestWeatherRepository()
    private val getWeatherForecastUseCase = GetWeatherForecastUseCase(weatherRepository)
    private val getCurrentWeatherUseCase = GetCurrentWeatherUseCase(weatherRepository)
    private val locationService = TestLocationService()
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        viewModel = WeatherViewModel(
            getCurrentWeatherUseCase,
            getWeatherForecastUseCase,
            locationService
        )
    }

    @Test
    fun test_when_fetch_location_fails_state_is_updated_to_stop_loading() = runTest {
        locationService.setLocationResult(
            ResultWrapper.Error(errorMessage = "Failed to get location")
        )
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.currentWeatherUIState.collect()
        }
        val uiState = viewModel.currentWeatherUIState.value
        assertFalse(uiState.isLoadingWithNoData)
        collectJob.cancel()
    }

    @Test
    fun test_when_get_current_Weather_fails_state_is_updated_with_error() = runTest {
        weatherRepository.setWeatherResult(
            ResultWrapper.Error("Failed")
        )
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.currentWeatherUIState.collect()
        }
        val uiState = viewModel.currentWeatherUIState.value
        assertFalse(uiState.isLoadingWithNoData)
        assertNotNull(uiState.errorMessage)
        collectJob.cancel()
    }

    @Test
    fun test_when_get_forecast_Weather_fails_state_is_updated_with_error() = runTest {
        weatherRepository.setForecastResult(
            ResultWrapper.Error("Failed")
        )
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.currentWeatherUIState.collect()
        }
        val uiState = viewModel.currentWeatherUIState.value
        assertFalse(uiState.isLoadingWithNoData)
        assertNotNull(uiState.errorMessage)
        collectJob.cancel()
    }

    @Test
    fun test_state_is_updated_on_fetching_current_weather_successfully() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.currentWeatherUIState.collect()
        }
        val uiState = viewModel.currentWeatherUIState.value
        assertFalse(uiState.isLoadingWithNoData)
        assertNull(uiState.errorMessage)
        Truth.assertThat(uiState.currentWeather?.id).isEqualTo(testWeather().id)
        collectJob.cancel()
    }

    @Test
    fun test_state_is_updated_on_fetching_forecast_weather_successfully() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.currentWeatherUIState.collect()
        }
        val uiState = viewModel.currentWeatherUIState.value
        assertFalse(uiState.isLoadingWithNoData)
        assertNull(uiState.errorMessage)
        Truth.assertThat(uiState.forecasts.size).isEqualTo(1)
        collectJob.cancel()
    }
}