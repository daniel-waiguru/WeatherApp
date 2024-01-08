package com.danielwaiguru.weatherapp.presentation.permissions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.danielwaiguru.weatherapp.presentation.R
import com.danielwaiguru.weatherapp.presentation.navigation.AppNavigationDestination
import com.danielwaiguru.weatherapp.presentation.permissions.PermissionsRoute
import com.danielwaiguru.weatherapp.presentation.weather.navigation.WeatherScreenDestination

object PermissionsScreenDestination : AppNavigationDestination {
    override val route: String = "com.danielwaiguru.weatherapp.PermissionsScreen"
    override val destination: String = "com.danielwaiguru.weatherapp.PermissionsScreenDestination"
    override val title: Int = R.string.grant_permissions
}

fun NavGraphBuilder.permissionsScreen(navController: NavHostController) {
    composable(route = PermissionsScreenDestination.route) {
        PermissionsRoute(
            onNavigateToWeather = {
                navController.navigate(WeatherScreenDestination.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        )
    }
}