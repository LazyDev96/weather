package com.example.weather.feature.home

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weather.core.testing.TestData
import com.example.weather.core.testing.string
import com.example.weather.core.ui.theme.AppTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class HomeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val events = mutableListOf<HomeEvent>()

    private fun setScreen(state: HomeState) {
        composeRule.setContent {
            AppTheme(dynamicColor = false) {
                HomeScreen(state = state, onEvent = { events += it })
            }
        }
    }

    private val weather = TestData.weather(TestData.kazan, temperature = 5.0)

    @Test
    fun `shows the current weather of the city`() {
        setScreen(HomeState(weather = weather))

        composeRule.onNodeWithText("Kazan").assertIsDisplayed()
        composeRule.onNodeWithText(string("home_temperature", 5)).assertIsDisplayed()
        composeRule.onNodeWithText(string("home_condition_cloudy")).assertIsDisplayed()
    }

    @Test
    fun `shows details with feels like, humidity and wind`() {
        setScreen(HomeState(weather = weather))

        val details = listOf(
            string("home_feels_like", 2),
            string("home_humidity", 80),
            string("home_wind", 12),
        ).joinToString(separator = " · ")
        composeRule.onNodeWithText(details).assertIsDisplayed()
    }

    @Test
    fun `shows the five day forecast`() {
        setScreen(HomeState(weather = weather))

        composeRule.onNodeWithText(string("home_forecast_title")).assertExists()
        composeRule.onNodeWithText(string("home_today")).assertExists()
        // max/min = 7° / 1°, одинаковы для всех пяти дней
        composeRule.onAllNodesWithText(string("home_temperature_range", 7, 1)).assertCountEquals(5)
    }

    @Test
    fun `shows when the data was updated`() {
        setScreen(HomeState(weather = weather))

        composeRule.onNode(androidx.compose.ui.test.hasText("Updated", substring = true)).assertExists()
    }

    @Test
    fun `error without data offers a retry`() {
        setScreen(HomeState(hasError = true))

        composeRule.onNodeWithText(string("home_error_title")).assertIsDisplayed()
        composeRule.onNodeWithText(string("home_retry")).performClick()

        assertEquals(listOf<HomeEvent>(HomeEvent.RefreshClicked), events)
    }

    @Test
    fun `error is not shown while a retry is in progress`() {
        setScreen(HomeState(hasError = true, isRefreshing = true))

        composeRule.onAllNodesWithText(string("home_error_title")).assertCountEquals(0)
        composeRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
    }

    @Test
    fun `first load shows only a progress indicator`() {
        setScreen(HomeState())

        composeRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
        composeRule.onAllNodesWithText(string("home_error_title")).assertCountEquals(0)
        composeRule.onAllNodesWithText(string("home_forecast_title")).assertCountEquals(0)
    }

    @Test
    fun `cached data stays visible with the error flag`() {
        setScreen(HomeState(weather = weather, hasError = true))

        composeRule.onNodeWithText("Kazan").assertIsDisplayed()
        composeRule.onAllNodesWithText(string("home_error_title")).assertCountEquals(0)
    }

    @Test
    fun `refresh button reports a click`() {
        setScreen(HomeState(weather = weather))

        composeRule.onNodeWithContentDescription(string("home_refresh")).performClick()

        assertEquals(listOf<HomeEvent>(HomeEvent.RefreshClicked), events)
    }

    @Test
    fun `refresh button is disabled while refreshing`() {
        setScreen(HomeState(weather = weather, isRefreshing = true))

        composeRule.onNodeWithContentDescription(string("home_refresh")).assertIsNotEnabled()
    }

    @Test
    fun `heart offers to add a city that is not a favorite`() {
        setScreen(HomeState(weather = weather, isFavorite = false))

        composeRule.onAllNodesWithContentDescription(string("home_favorite_remove")).assertCountEquals(0)
        composeRule.onNodeWithContentDescription(string("home_favorite_add")).performClick()

        assertEquals(listOf<HomeEvent>(HomeEvent.FavoriteClicked), events)
    }

    @Test
    fun `heart offers to remove a city that is a favorite`() {
        setScreen(HomeState(weather = weather, isFavorite = true))

        composeRule.onAllNodesWithContentDescription(string("home_favorite_add")).assertCountEquals(0)
        composeRule.onNodeWithContentDescription(string("home_favorite_remove")).performClick()

        assertEquals(listOf<HomeEvent>(HomeEvent.FavoriteClicked), events)
    }
}
