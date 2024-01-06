package com.danielwaiguru.weatherapp.domain.usecases

import com.danielwaiguru.weatherapp.domain.location.LocationService
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetCurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val locationService: LocationService
) {
    suspend operator fun invoke(): Flow<ResultWrapper<Weather?>> {
        val userLocation = locationService.getCurrentLocation()
        return if (userLocation is ResultWrapper.Success && userLocation.value != null) {
            weatherRepository.getCurrentWeather(userLocation.value)
        } else {
            flowOf(
                ResultWrapper.Error(
                    errorMessage = (userLocation as ResultWrapper.Error).errorMessage
                )
            )
        }
    }
}