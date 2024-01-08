package com.danielwaiguru.weatherapp.domain.usecases

import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

class GetCurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(userLocation: UserLocation): Flow<ResultWrapper<Weather?>> {
        return weatherRepository.getCurrentWeather(userLocation)
    }
}
