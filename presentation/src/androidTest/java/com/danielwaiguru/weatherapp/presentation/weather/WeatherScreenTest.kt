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