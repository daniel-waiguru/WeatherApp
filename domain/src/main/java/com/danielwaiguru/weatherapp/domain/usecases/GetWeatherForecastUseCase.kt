package com.danielwaiguru.weatherapp.domain.usecases

import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast
import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWeatherForecastUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(userLocation: UserLocation): Flow<ResultWrapper<List<WeatherForecast>>> {
        return weatherRepository.getWeatherForecast(userLocation)
            .map { result ->
                when (result) {
                    is ResultWrapper.Error -> {
                        ResultWrapper.Error(
                            data = result.data?.distinctBy { it.day },
                            errorMessage = result.errorMessage
                        )
                    }
                    is ResultWrapper.Loading -> {
                        ResultWrapper.Loading(data = result.data?.distinctBy { it.day })
                    }
                    is ResultWrapper.Success -> {
                        ResultWrapper.Success(result.data?.distinctBy { it.day } ?: emptyList())
                    }
                }
            }
    }
}
