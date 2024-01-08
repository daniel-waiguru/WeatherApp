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
): ViewModel() {
    private val _currentWeatherUIState = MutableStateFlow(WeatherScreenState())
    val currentWeatherUIState: StateFlow<WeatherScreenState> = _currentWeatherUIState.asStateFlow()

    init {
        getCurrentLocation()
    }

    private fun getWeatherForecast(userLocation: UserLocation) {
        viewModelScope.launch {
            getWeatherForecastUseCase(userLocation)
                .onEach { result ->
                    when(result) {
                        is ResultWrapper.Error -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = result.errorMessage,
                            )
                        }
                        is ResultWrapper.Loading -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                        is ResultWrapper.Success -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = null,
                                forecasts = result.data
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
                    when(result) {
                        is ResultWrapper.Error -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = result.errorMessage,
                            )
                        }
                        is ResultWrapper.Loading -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = true,
                                errorMessage = null
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
            when(val userLocationResult = locationService.getCurrentLocation()) {
                is ResultWrapper.Error -> {
                    _currentWeatherUIState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = userLocationResult.errorMessage
                        )
                    }
                }
                ResultWrapper.Loading -> Unit
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