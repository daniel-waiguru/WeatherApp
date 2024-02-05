/*
 * MIT License
 *
 * Copyright (c) 2024 Daniel Waiguru
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.danielwaiguru.weatherapp.data.di

import com.danielwaiguru.weatherapp.data.location.AndroidLocationService
import com.danielwaiguru.weatherapp.data.sources.local.LocalDataSource
import com.danielwaiguru.weatherapp.data.sources.local.RoomDataSource
import com.danielwaiguru.weatherapp.data.sources.remote.RemoteDataSource
import com.danielwaiguru.weatherapp.data.sources.remote.RetrofitDataSource
import com.danielwaiguru.weatherapp.domain.location.LocationService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
    @[
        Singleton
        Binds
    ]
    internal abstract fun bindRemoteDataSource(
        retrofitDataSource: RetrofitDataSource
    ): RemoteDataSource

    @[
        Singleton
        Binds
    ]
    internal abstract fun bindLocalDataSource(
        roomDataSource: RoomDataSource
    ): LocalDataSource

    @[
        Singleton
        Binds
    ]
    internal abstract fun bindLocationService(
        androidLocationService: AndroidLocationService
    ): LocationService
}
