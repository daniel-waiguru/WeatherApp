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
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume

internal class AndroidLocationService(
    @ApplicationContext private val context: Context,
): LocationService {
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