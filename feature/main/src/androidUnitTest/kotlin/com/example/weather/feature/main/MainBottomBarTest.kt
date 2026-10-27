package com.example.weather.feature.main

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
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
class MainBottomBarTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val clicked = mutableListOf<TopLevelDestination>()

    private fun setBar(selected: TopLevelDestination) {
        composeRule.setContent {
            AppTheme(dynamicColor = false) {
                MainBottomBar(isSelected = { it == selected }, onTabClick = { clicked += it })
            }
        }
    }

    private fun label(tab: TopLevelDestination) = string(tab.labelRes.key)

    @Test
    fun `shows exactly the four tabs`() {
        setBar(TopLevelDestination.HOME)

        TopLevelDestination.entries.forEach { tab ->
            composeRule.onNodeWithText(label(tab)).assertIsDisplayed()
        }
        assertEquals(
            listOf(TopLevelDestination.HOME, TopLevelDestination.EXPLORE, TopLevelDestination.FAVORITES, TopLevelDestination.SETTINGS),
            TopLevelDestination.entries.toList(),
        )
    }

    @Test
    fun `alerts tab is gone`() {
        setBar(TopLevelDestination.HOME)

        composeRule.onAllNodesWithText("Alerts").assertCountEquals(0)
    }

    @Test
    fun `only the current tab is selected`() {
        setBar(TopLevelDestination.FAVORITES)

        composeRule.onNode(hasText(label(TopLevelDestination.FAVORITES))).assertIsSelected()
        composeRule.onNode(hasText(label(TopLevelDestination.HOME))).assertIsNotSelected()
        composeRule.onNode(hasText(label(TopLevelDestination.SETTINGS))).assertIsNotSelected()
    }

    @Test
    fun `clicking a tab reports it`() {
        setBar(TopLevelDestination.HOME)

        composeRule.onNodeWithText(label(TopLevelDestination.SETTINGS)).performClick()

        assertEquals(listOf(TopLevelDestination.SETTINGS), clicked)
    }
}
