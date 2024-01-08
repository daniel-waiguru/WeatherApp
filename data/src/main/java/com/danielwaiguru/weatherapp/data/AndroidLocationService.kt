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

package com.danielwaiguru.weatherapp.data

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import com.danielwaiguru.weatherapp.domain.location.LocationService
import com.danielwaiguru.weatherapp.domain.models.UserLocation
import com.danielwaiguru.weatherapp.domain.utils.ResultWrapper
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber

internal class AndroidLocationService @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationService {
    private val locationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    override val isGpsEnabled: Boolean
        get() {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): ResultWrapper<UserLocation?> {
        if (isGpsEnabled.not()) {
            return ResultWrapper.Error(
                errorMessage = "Failed to get location. Enable gps services"
            )
        }
        if (hasLocationPermission().not()) {
            return ResultWrapper.Error(
                errorMessage = "Failed to get location. Please grant location permission."
            )
        }
        return suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation
                .apply {
                    if (isComplete) {
                        if (isSuccessful) {
                            continuation.resume(
                                ResultWrapper.Success(
                                    UserLocation(
                                        latitude = result.latitude,
                                        longitude = result.longitude
                                    )
                                )
                            )
                        } else {
                            continuation.resume(
                                ResultWrapper.Error(
                                    errorMessage = exception?.message
                                )
                            )
                        }
                        return@suspendCancellableCoroutine
                    }
                    addOnSuccessListener {
                        continuation.resume(
                            ResultWrapper.Success(
                                if (it != null) {
                                    UserLocation(
                                        latitude = it.latitude,
                                        longitude = it.longitude
                                    )
                                } else {
                                    null
                                }

                            )
                        )
                    }
                    addOnFailureListener {
                        Timber.i(it)
                        continuation.resume(
                            ResultWrapper.Error(
                                errorMessage = it.message
                            )
                        )
                    }
                    addOnCanceledListener {
                        continuation.cancel()
                    }
                }
        }
    }

    private fun hasLocationPermission(): Boolean {
        return context.checkSelfPermission(
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}
