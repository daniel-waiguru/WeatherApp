package com.danielwaiguru.weatherapp.data.repositories

import com.danielwaiguru.weatherapp.data.sources.remote.RemoteDataSource
import com.danielwaiguru.weatherapp.domain.models.Coordinates
import com.danielwaiguru.weatherapp.domain.models.FiveDayWeatherForecast
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import com.danielwaiguru.weatherapp.domain.utils.Dispatcher
import com.danielwaiguru.weatherapp.domain.utils.DispatcherProvider
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

internal class WeatherRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    @Dispatcher(DispatcherProvider.IO) private val ioDispatcher: CoroutineDispatcher
): WeatherRepository {
    override suspend fun getCurrentWeather(
        coordinates: Coordinates
    ): Flow<ResultWrapper<Weather?>> = flow{
        withContext(ioDispatcher) {
            val currentWeather = remoteDataSource.getCurrentWeather(
                latitude = coordinates.latitude,
                longitude = coordinates.longitude
            )
        }
    }

    override suspend fun getWeatherForecast(
        coordinates: Coordinates
    ): Flow<ResultWrapper<List<FiveDayWeatherForecast>>> {
        TODO("Not yet implemented")
    }
}