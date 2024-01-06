package com.danielwaiguru.weatherapp.data.di

import android.content.Context
import androidx.room.Room
import com.danielwaiguru.weatherapp.data.sources.local.daos.WeatherDao
import com.danielwaiguru.weatherapp.data.sources.local.database.WeatherAppDatabase
import com.danielwaiguru.weatherapp.data.sources.local.database.WeatherAppDatabase.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {
    @[Provides Singleton]
    internal fun provideWeatherAppDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            WeatherAppDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()

    @[Provides Singleton]
    fun provideTouristDao(database: WeatherAppDatabase): WeatherDao = database.weatherDao()
}