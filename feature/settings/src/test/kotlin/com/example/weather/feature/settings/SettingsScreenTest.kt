package com.example.weather.feature.settings

import com.example.weather.core.data.model.AppLanguage
import com.example.weather.core.data.model.AppSettings
import com.example.weather.core.data.model.ThemeMode
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weather.core.testing.string
import com.example.weather.core.ui.theme.AppTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class SettingsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val events = mutableListOf<SettingsEvent>()

    private fun setScreen(settings: AppSettings = AppSettings()) {
        composeRule.setContent {
            AppTheme(dynamicColor = false) {
                SettingsScreen(state = SettingsState(settings), onEvent = { events += it })
            }
        }
    }

    @Test
    fun `shows the language and theme settings with defaults`() {
        setScreen()

        composeRule.onNodeWithText(string(R.string.settings_title)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.settings_language)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.settings_language_english)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.settings_theme)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.settings_theme_system)).assertIsDisplayed()
    }

    @Test
    fun `shows the stored values`() {
        setScreen(AppSettings(language = AppLanguage.RUSSIAN, theme = ThemeMode.DARK))

        composeRule.onNodeWithText(string(R.string.settings_language_russian)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.settings_theme_dark)).assertIsDisplayed()
    }

    @Test
    fun `language row opens the language screen`() {
        setScreen()

        composeRule.onNodeWithText(string(R.string.settings_language)).performClick()

        assertEquals(listOf<SettingsEvent>(SettingsEvent.LanguageClicked), events)
    }

    @Test
    fun `theme row opens the theme screen`() {
        setScreen()

        composeRule.onNodeWithText(string(R.string.settings_theme)).performClick()

        assertEquals(listOf<SettingsEvent>(SettingsEvent.ThemeClicked), events)
    }

    @Test
    fun `weather notification switch turns it off when it is on`() {
        setScreen(AppSettings(weatherNotificationEnabled = true))

        composeRule.onNodeWithText(string(R.string.settings_weather_notification)).performClick()

        assertEquals(listOf<SettingsEvent>(SettingsEvent.WeatherNotificationToggled(false)), events)
    }

    @Test
    fun `weather notification switch turns it on when it is off`() {
        setScreen(AppSettings(weatherNotificationEnabled = false))

        composeRule.onNodeWithText(string(R.string.settings_weather_notification)).performClick()

        assertEquals(listOf<SettingsEvent>(SettingsEvent.WeatherNotificationToggled(true)), events)
    }
}
