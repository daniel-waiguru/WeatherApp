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

package com.danielwaiguru.weatherapp.testing.location

import com.danielwaiguru.weatherapp.domain.location.LocationService
import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import com.danielwaiguru.weatherapp.testing.testData.testLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class TestLocationService : LocationService {
    private val currentLocation = MutableStateFlow<ResultWrapper<UserLocation?>>(
        ResultWrapper.Success(testLocation),
    )
    private val isGpsEnabledResult = MutableStateFlow(true)
    override suspend fun getCurrentLocation(): ResultWrapper<UserLocation?> = currentLocation.first()

    override val isGpsEnabled: Boolean
        get() = isGpsEnabledResult.value

    fun setLocationResult(result: ResultWrapper<UserLocation?>) {
        currentLocation.update { result }
    }

    fun setGpsResult(enabled: Boolean) {
        isGpsEnabledResult.update { enabled }
    }
}