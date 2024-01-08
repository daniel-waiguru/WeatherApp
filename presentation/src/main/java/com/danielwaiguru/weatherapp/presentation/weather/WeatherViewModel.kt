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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielwaiguru.weatherapp.domain.location.LocationService
import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.usecases.GetCurrentWeatherUseCase
import com.danielwaiguru.weatherapp.domain.usecases.GetWeatherForecastUseCase
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val locationService: LocationService
) : ViewModel() {
    private val _currentWeatherUIState = MutableStateFlow(WeatherScreenState())
    val currentWeatherUIState: StateFlow<WeatherScreenState> = _currentWeatherUIState.asStateFlow()

    init {
        getCurrentLocation()
    }

    private fun getWeatherForecast(userLocation: UserLocation) {
        viewModelScope.launch {
            getWeatherForecastUseCase(userLocation)
                .onEach { result ->
                    when (result) {
                        is ResultWrapper.Error -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = result.errorMessage,
                                forecasts = result.data ?: emptyList()
                            )
                        }
                        is ResultWrapper.Loading -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = true,
                                errorMessage = null,
                                forecasts = result.data ?: emptyList()
                            )
                        }
                        is ResultWrapper.Success -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = null,
                                forecasts = result.data ?: emptyList()
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun getCurrentWeather(userLocation: UserLocation) {
        viewModelScope.launch {
            getCurrentWeatherUseCase(userLocation)
                .onEach { result ->
                    when (result) {
                        is ResultWrapper.Error -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = result.errorMessage,
                                currentWeather = result.data
                            )
                        }
                        is ResultWrapper.Loading -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = true,
                                errorMessage = null,
                                currentWeather = result.data
                            )
                        }
                        is ResultWrapper.Success -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = null,
                                currentWeather = result.data
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            _currentWeatherUIState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            when (val userLocationResult = locationService.getCurrentLocation()) {
                is ResultWrapper.Error -> {
                    _currentWeatherUIState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = userLocationResult.errorMessage
                        )
                    }
                }
                is ResultWrapper.Loading -> Unit
                is ResultWrapper.Success -> {
                    if (userLocationResult.data == null) {
                        _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = "No location found"
                            )
                        }
                        return@launch
                    }
                    val userLocation = userLocationResult.data!!
                    getCurrentWeather(userLocation)
                    getWeatherForecast(userLocation)
                }
            }
        }
    }
}