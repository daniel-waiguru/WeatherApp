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

class TestWeatherRepository: WeatherRepository {
    private val weatherResult = MutableStateFlow<ResultWrapper<Weather?>>(
        ResultWrapper.Success(
            testWeather()
        )
    )

    private val forecastResult = MutableStateFlow<ResultWrapper<List<WeatherForecast>>>(
        ResultWrapper.Success(
            listOf(
                testForecast(),
                testForecast(
                    id = 4,
                    date = 1704803010,
                    conditionId = 200
                )
            )
        )
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