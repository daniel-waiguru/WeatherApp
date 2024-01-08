package com.danielwaiguru.weatherapp.data.di

import com.danielwaiguru.weatherapp.data.repositories.WeatherRepositoryImpl
import com.danielwaiguru.weatherapp.domain.repositories.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesBindings {
    @[
        Binds
        Singleton
    ]
    internal abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
