package com.example.weather.feature.explore

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.semantics.ProgressBarRangeInfo
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
class ExploreScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val events = mutableListOf<ExploreEvent>()

    private fun setScreen(state: ExploreState) {
        composeRule.setContent {
            AppTheme(dynamicColor = false) {
                ExploreScreen(state = state, onEvent = { events += it })
            }
        }
    }

    @Test
    fun `idle state invites to search`() {
        setScreen(ExploreState())

        composeRule.onNodeWithText(string("explore_idle_title")).assertIsDisplayed()
        composeRule.onNodeWithText(string("explore_idle_hint")).assertIsDisplayed()
    }

    @Test
    fun `typing reports the new query`() {
        setScreen(ExploreState())

        composeRule.onNode(hasSetTextAction()).performTextInput("Kaz")

        assertEquals(listOf<ExploreEvent>(ExploreEvent.QueryChanged("Kaz")), events)
    }

    @Test
    fun `shows found cities with regions`() {
        setScreen(
            ExploreState(
                query = "Ka",
                results = listOf(TestData.kazan, TestData.samara),
                status = SearchStatus.SUCCESS,
            ),
        )

        composeRule.onNodeWithText("Kazan").assertIsDisplayed()
        composeRule.onNodeWithText("Tatarstan").assertIsDisplayed()
        composeRule.onNodeWithText("Samara").assertIsDisplayed()
        composeRule.onAllNodesWithText(string("explore_idle_title")).assertCountEquals(0)
    }

    @Test
    fun `marks only the selected city`() {
        setScreen(
            ExploreState(
                results = listOf(TestData.kazan, TestData.samara),
                status = SearchStatus.SUCCESS,
                selectedId = TestData.kazan.id,
            ),
        )

        composeRule.onAllNodesWithContentDescription(string("explore_selected")).assertCountEquals(1)
    }

    @Test
    fun `clicking a result reports it`() {
        setScreen(ExploreState(results = listOf(TestData.kazan), status = SearchStatus.SUCCESS))

        composeRule.onNodeWithText("Kazan").performClick()

        assertEquals(listOf<ExploreEvent>(ExploreEvent.ResultClicked(TestData.kazan)), events)
    }

    @Test
    fun `empty result explains that nothing was found`() {
        setScreen(ExploreState(query = "Zzzz", status = SearchStatus.SUCCESS))

        composeRule.onNodeWithText(string("explore_empty_title")).assertIsDisplayed()
        composeRule.onNodeWithText(string("explore_empty_hint")).assertIsDisplayed()
    }

    @Test
    fun `error offers a retry`() {
        setScreen(ExploreState(query = "Kazan", status = SearchStatus.ERROR))

        composeRule.onNodeWithText(string("explore_error_title")).assertIsDisplayed()
        composeRule.onNodeWithText(string("explore_retry")).performClick()

        assertEquals(listOf<ExploreEvent>(ExploreEvent.RetryClicked), events)
    }

    @Test
    fun `loading shows a progress bar and no messages`() {
        setScreen(ExploreState(query = "Kazan", status = SearchStatus.LOADING))

        composeRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
        composeRule.onAllNodesWithText(string("explore_idle_title")).assertCountEquals(0)
        composeRule.onAllNodesWithText(string("explore_empty_title")).assertCountEquals(0)
        composeRule.onAllNodesWithText(string("explore_error_title")).assertCountEquals(0)
    }
}
