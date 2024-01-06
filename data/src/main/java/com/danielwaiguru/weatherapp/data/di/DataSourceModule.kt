package com.danielwaiguru.weatherapp.data.di

import com.danielwaiguru.weatherapp.data.sources.local.LocalDataSource
import com.danielwaiguru.weatherapp.data.sources.local.RoomDataSource
import com.danielwaiguru.weatherapp.data.sources.remote.RemoteDataSource
import com.danielwaiguru.weatherapp.data.sources.remote.RetrofitDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
    @[Singleton Binds]
    internal abstract fun bindRemoteDataSource(
        retrofitDataSource: RetrofitDataSource
    ): RemoteDataSource

    @[Singleton Binds]
    internal abstract fun bindLocalDataSource(
        roomDataSource: RoomDataSource
    ): LocalDataSource
}