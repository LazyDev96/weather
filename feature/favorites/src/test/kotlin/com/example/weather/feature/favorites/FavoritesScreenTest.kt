package com.example.weather.feature.favorites

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
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
class FavoritesScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val events = mutableListOf<FavoritesEvent>()

    private fun setScreen(state: FavoritesState) {
        composeRule.setContent {
            AppTheme(dynamicColor = false) {
                FavoritesScreen(state = state, onEvent = { events += it })
            }
        }
    }

    @Test
    fun `shows the title while loading and nothing else`() {
        setScreen(FavoritesState(isLoading = true))

        composeRule.onNodeWithText(string(R.string.favorites_title)).assertIsDisplayed()
        composeRule.onAllNodesWithText(string(R.string.favorites_empty)).assertCountEquals(0)
    }

    @Test
    fun `shows the hint when there are no favorites`() {
        setScreen(FavoritesState(isLoading = false))

        composeRule.onNodeWithText(string(R.string.favorites_empty)).assertIsDisplayed()
    }

    @Test
    fun `lists favorite cities with their regions`() {
        setScreen(FavoritesState(items = listOf(TestData.kazan, TestData.samara), isLoading = false))

        composeRule.onNodeWithText("Kazan").assertIsDisplayed()
        composeRule.onNodeWithText("Tatarstan").assertIsDisplayed()
        composeRule.onNodeWithText("Samara").assertIsDisplayed()
        composeRule.onAllNodesWithText(string(R.string.favorites_empty)).assertCountEquals(0)
    }

    @Test
    fun `clicking a city reports it`() {
        setScreen(FavoritesState(items = listOf(TestData.kazan, TestData.samara), isLoading = false))

        composeRule.onNodeWithText("Samara").performClick()

        assertEquals(listOf<FavoritesEvent>(FavoritesEvent.LocationClicked(TestData.samara)), events)
    }

    @Test
    fun `remove button reports the id of its own row`() {
        setScreen(FavoritesState(items = listOf(TestData.kazan, TestData.samara), isLoading = false))

        composeRule.onAllNodesWithContentDescription(string(R.string.favorites_remove))[1].performClick()

        assertEquals(listOf<FavoritesEvent>(FavoritesEvent.RemoveClicked(TestData.samara.id)), events)
    }
}
