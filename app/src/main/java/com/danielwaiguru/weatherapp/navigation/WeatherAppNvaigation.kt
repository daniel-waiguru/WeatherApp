package com.danielwaiguru.weatherapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.danielwaiguru.weatherapp.designsystem.theme.WeatherAppTheme
import com.danielwaiguru.weatherapp.presentation.permissions.navigation.PermissionsScreenDestination
import com.danielwaiguru.weatherapp.presentation.permissions.navigation.permissionsScreen
import com.danielwaiguru.weatherapp.presentation.weather.navigation.weatherScreen

@Composable
fun WeatherApp() {
    WeatherAppTheme {
        val navController = rememberNavController()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = PermissionsScreenDestination.route
            ) {
                permissionsScreen(navController = navController)
                weatherScreen()
            }
        }
    }
}