package com.example.weather.feature.splash

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weather.core.testing.string
import com.example.weather.core.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class SplashScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `shows the logo`() {
        composeRule.setContent { AppTheme(dynamicColor = false) { SplashScreen() } }

        composeRule
            .onNodeWithContentDescription(string("splash_image_description"))
            .assertIsDisplayed()
    }
}
