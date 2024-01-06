package com.danielwaiguru.weatherapp.data.repositories

import com.danielwaiguru.weatherapp.data.mappers.toForecastEntity
import com.danielwaiguru.weatherapp.data.mappers.toWeather
import com.danielwaiguru.weatherapp.data.mappers.toWeatherEntity
import com.danielwaiguru.weatherapp.data.mappers.toWeatherForecast
import com.danielwaiguru.weatherapp.data.models.entities.ForecastEntity
import com.danielwaiguru.weatherapp.data.sources.local.LocalDataSource
import com.danielwaiguru.weatherapp.data.sources.remote.RemoteDataSource
import com.danielwaiguru.weatherapp.data.utils.networkBoundResource
import com.danielwaiguru.weatherapp.domain.models.Coordinates
import com.danielwaiguru.weatherapp.domain.models.Weather
import com.danielwaiguru.weatherapp.domain.models.WeatherForecast
import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import com.danielwaiguru.weatherapp.domain.utils.Dispatcher
import com.danielwaiguru.weatherapp.domain.utils.DispatcherProvider
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class WeatherRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @Dispatcher(DispatcherProvider.IO) private val ioDispatcher: CoroutineDispatcher
): WeatherRepository {
    override suspend fun getCurrentWeather(
        coordinates: Coordinates
    ): Flow<ResultWrapper<Weather?>> = networkBoundResource(
        query = {
                localDataSource.getCurrentWeather()
                    .map { weatherEntity -> weatherEntity?.toWeather() }
        },
        fetch = {
            remoteDataSource.getCurrentWeather(
                longitude = coordinates.longitude,
                latitude = coordinates.latitude
            )
        },
        saveFetchResult = { weatherResponse ->
            val currentWeatherEntity = weatherResponse.toWeatherEntity()
            withContext(NonCancellable) {
                localDataSource.saveWeather(currentWeatherEntity)
            }
        }
    ).flowOn(ioDispatcher)

    override suspend fun getWeatherForecast(
        coordinates: Coordinates
    ): Flow<ResultWrapper<List<WeatherForecast>>> = networkBoundResource(
        query = {
            localDataSource.getWeatherForecast()
                .map { entities -> entities.map(ForecastEntity::toWeatherForecast) }

        },
        fetch = {
            remoteDataSource.getWeatherForecast(
                latitude = coordinates.latitude,
                longitude = coordinates.longitude
            )
        },
        saveFetchResult = { forecastResponse ->
            val forecastEntity = forecastResponse.toForecastEntity()
            withContext(NonCancellable) {
                localDataSource.saveWeatherForecast(forecastEntity)
            }
        }
    )
}