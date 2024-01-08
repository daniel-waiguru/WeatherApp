package com.danielwaiguru.weatherapp.data.di

import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import com.danielwaiguru.weatherapp.domain.usecases.GetCurrentWeatherUseCase
import com.danielwaiguru.weatherapp.domain.usecases.GetWeatherForecastUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {
    @[
        Provides
        ViewModelScoped
    ]
    fun provideGetCurrentWeatherUseCase(
        weatherRepository: WeatherRepository
    ): GetCurrentWeatherUseCase = GetCurrentWeatherUseCase(weatherRepository)

    @[
        Provides
        ViewModelScoped
    ]
    fun provideGetWeatherForecastUseCase(
        weatherRepository: WeatherRepository
    ): GetWeatherForecastUseCase = GetWeatherForecastUseCase(weatherRepository)
}
