package com.danielwaiguru.weatherapp.presentation.weather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.danielwaiguru.weatherapp.designsystem.testtags.TestTags
import com.danielwaiguru.weatherapp.testing.testData.testWeather
import org.junit.Rule
import org.junit.Test

class WeatherScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun loading_state_is_handled_by_displaying_progress_indicator() {
        rule.setContent {
            WeatherScreen(
                state = WeatherScreenState(isLoading = true)
            )
        }
        rule.onNodeWithTag(TestTags.ProgressIndicator).assertIsDisplayed()
    }

    @Test
    fun current_weather_is_displayed_when_its_not_null() {
        rule.setContent {
            WeatherScreen(
                state = WeatherScreenState(
                    isLoading = false,
                    currentWeather = testWeather()
                )
            )
        }
        rule.onNodeWithTag(TestTags.CurrentWeather).assertIsDisplayed()
        rule.onNodeWithTag(TestTags.TempText).assertTextEquals("${testWeather().temp}°C")
    }
    @Test
    fun error_state_is_handled_by_displayed_a_snackbar() {
        rule.setContent {
            WeatherScreen(
                state = WeatherScreenState(
                    isLoading = false,
                    errorMessage = "Failed. Try again"
                )
            )
        }
        rule.onNodeWithTag(TestTags.Snackbar).assertIsDisplayed()
    }
}