package com.danielwaiguru.weatherapp.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielwaiguru.weatherapp.domain.usecases.GetCurrentWeatherUseCase
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
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
): ViewModel() {
    private val _currentWeatherUIState = MutableStateFlow(WeatherScreenState())
    val currentWeatherUIState: StateFlow<WeatherScreenState> = _currentWeatherUIState.asStateFlow()

    init {
        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        viewModelScope.launch {
            _currentWeatherUIState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            getCurrentWeatherUseCase()
                .onEach { result ->
                    when(result) {
                        is ResultWrapper.Error -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                errorMessage = result.errorMessage,
                                currentWeather = result.data
                            )
                        }
                        is ResultWrapper.Loading -> _currentWeatherUIState.update { currentState ->
                            currentState.copy(
                                isLoading = result.data != null,
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
}