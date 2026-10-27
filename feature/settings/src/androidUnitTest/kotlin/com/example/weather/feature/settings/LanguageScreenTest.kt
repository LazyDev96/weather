package com.example.weather.feature.settings

import com.example.weather.core.data.model.AppLanguage
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
class LanguageScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val events = mutableListOf<LanguageEvent>()

    private fun setScreen(selected: AppLanguage = AppLanguage.ENGLISH) {
        composeRule.setContent {
            AppTheme(dynamicColor = false) {
                LanguageScreen(state = LanguageState(selected), onEvent = { events += it })
            }
        }
    }

    @Test
    fun `lists exactly russian and english`() {
        setScreen()

        composeRule.onNodeWithText(string("settings_language_russian")).assertIsDisplayed()
        composeRule.onNodeWithText(string("settings_language_english")).assertIsDisplayed()
    }

    @Test
    fun `english is selected by default`() {
        setScreen()

        composeRule.onNodeWithText(string("settings_language_english")).assertIsSelected()
        composeRule.onNodeWithText(string("settings_language_russian")).assertIsNotSelected()
    }

    @Test
    fun `selection follows the state`() {
        setScreen(AppLanguage.RUSSIAN)

        composeRule.onNodeWithText(string("settings_language_russian")).assertIsSelected()
        composeRule.onNodeWithText(string("settings_language_english")).assertIsNotSelected()
    }

    @Test
    fun `choosing russian reports it`() {
        setScreen()

        composeRule.onNodeWithText(string("settings_language_russian")).performClick()

        assertEquals(listOf<LanguageEvent>(LanguageEvent.LanguageSelected(AppLanguage.RUSSIAN)), events)
    }

    @Test
    fun `back button reports a click`() {
        setScreen()

        composeRule.onNodeWithContentDescription(string("settings_back")).performClick()

        assertEquals(listOf<LanguageEvent>(LanguageEvent.BackClicked), events)
    }
}
