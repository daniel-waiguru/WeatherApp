package com.danielwaiguru.weatherapp.presentation.permissions

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.danielwaiguru.weatherapp.designsystem.components.PrimaryButton
import com.danielwaiguru.weatherapp.designsystem.components.TopAppBar
import com.danielwaiguru.weatherapp.presentation.R
import com.danielwaiguru.weatherapp.presentation.permissions.navigation.PermissionsScreenDestination
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun PermissionsRoute(onNavigateToWeather: () -> Unit) {
    PermissionsScreen(
        onNavigateToWeather = onNavigateToWeather,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    )
}

val locationPermissions = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsScreen(
    onNavigateToWeather: () -> Unit,
    modifier: Modifier = Modifier
) {
    val locationPermissionsState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(key1 = locationPermissionsState.status) {
        if (locationPermissionsState.status.isGranted) {
            onNavigateToWeather()
        }
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.location_animation)
    )
    val animationState = animateLottieCompositionAsState(composition = composition)
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(id = PermissionsScreenDestination.title)
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.location_permission),
                textAlign = TextAlign.Center
            )
            LottieAnimation(
                composition = composition,
                progress = { animationState.progress },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(300.dp),
                contentScale = ContentScale.Fit
            )
            PrimaryButton(
                text = stringResource(id = R.string.get_started),
                onClick = {
                    locationPermissionsState.launchPermissionRequest()
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
