package com.example.weather.feature.settings

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
class ThemeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val events = mutableListOf<ThemeEvent>()

    private fun setScreen(selected: ThemeMode = ThemeMode.SYSTEM) {
        composeRule.setContent {
            AppTheme(dynamicColor = false) {
                ThemeScreen(state = ThemeState(selected), onEvent = { events += it })
            }
        }
    }

    @Test
    fun `lists light, dark and system themes`() {
        setScreen()

        composeRule.onNodeWithText(string(R.string.settings_theme_light)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.settings_theme_dark)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.settings_theme_system)).assertIsDisplayed()
    }

    @Test
    fun `system theme is selected by default`() {
        setScreen()

        composeRule.onNodeWithText(string(R.string.settings_theme_system)).assertIsSelected()
        composeRule.onNodeWithText(string(R.string.settings_theme_light)).assertIsNotSelected()
        composeRule.onNodeWithText(string(R.string.settings_theme_dark)).assertIsNotSelected()
    }

    @Test
    fun `selection follows the state`() {
        setScreen(ThemeMode.DARK)

        composeRule.onNodeWithText(string(R.string.settings_theme_dark)).assertIsSelected()
        composeRule.onNodeWithText(string(R.string.settings_theme_system)).assertIsNotSelected()
    }

    @Test
    fun `choosing a theme reports it`() {
        setScreen()

        composeRule.onNodeWithText(string(R.string.settings_theme_light)).performClick()

        assertEquals(listOf<ThemeEvent>(ThemeEvent.ThemeSelected(ThemeMode.LIGHT)), events)
    }

    @Test
    fun `back button reports a click`() {
        setScreen()

        composeRule.onNodeWithContentDescription(string(R.string.settings_back)).performClick()

        assertEquals(listOf<ThemeEvent>(ThemeEvent.BackClicked), events)
    }
}
