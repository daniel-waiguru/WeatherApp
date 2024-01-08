package com.danielwaiguru.weatherapp.presentation.permissions

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.danielwaiguru.weatherapp.designsystem.testtags.TestTags
import org.junit.Rule
import org.junit.Test

class PermissionsScreenTest {
    @get:Rule
    val rule = createComposeRule()
    @Test
    fun assert_get_started_button_has_click_action() {
        rule.setContent {
            PermissionsScreen(
                onNavigateToWeather = {  }
            )
        }
        rule.onNodeWithTag(TestTags.PrimaryButton).assertHasClickAction()
    }

}