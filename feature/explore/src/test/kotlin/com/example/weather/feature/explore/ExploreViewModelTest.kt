package com.example.weather.feature.explore

import app.cash.turbine.test
import com.example.weather.core.data.LocationRepository
import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.testing.MainDispatcherRule
import com.example.weather.core.testing.TestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ExploreViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val selectedFlow = MutableStateFlow(TestData.moscow)
    private val repository = mockk<LocationRepository>(relaxUnitFun = true) {
        every { observeSelected() } returns selectedFlow
    }

    private fun createViewModel() = ExploreViewModel(repository)

    /** Поиск стартует через 400 мс после последнего символа. */
    private fun TestScope.passDebounce() = advanceTimeBy(401)

    private fun stubSearch(result: Result<List<WeatherLocation>>) {
        coEvery { repository.search(any()) } returns result
    }

    @Test
    fun `selected city is taken from the repository`() = runTest {
        val viewModel = createViewModel()
        assertEquals(TestData.moscow.id, viewModel.state.value.selectedId)

        selectedFlow.value = TestData.kazan
        assertEquals(TestData.kazan.id, viewModel.state.value.selectedId)
    }

    @Test
    fun `starts idle without results`() = runTest {
        val state = createViewModel().state.value

        assertEquals(SearchStatus.IDLE, state.status)
        assertTrue(state.results.isEmpty())
    }

    @Test
    fun `query shorter than three letters does not hit the network`() = runTest {
        val viewModel = createViewModel()

        viewModel.onEvent(ExploreEvent.QueryChanged("Ka"))
        passDebounce()

        coVerify(exactly = 0) { repository.search(any()) }
        assertEquals("Ka", viewModel.state.value.query)
        assertEquals(SearchStatus.IDLE, viewModel.state.value.status)
    }

    @Test
    fun `long enough query is searched after the debounce`() = runTest {
        stubSearch(Result.success(listOf(TestData.kazan)))
        val viewModel = createViewModel()

        viewModel.onEvent(ExploreEvent.QueryChanged("Kaz"))
        advanceTimeBy(300)
        coVerify(exactly = 0) { repository.search(any()) }

        passDebounce()

        coVerify(exactly = 1) { repository.search("Kaz") }
        assertEquals(SearchStatus.SUCCESS, viewModel.state.value.status)
        assertEquals(listOf(TestData.kazan), viewModel.state.value.results)
    }

    @Test
    fun `typing quickly sends only the last query`() = runTest {
        stubSearch(Result.success(listOf(TestData.kazan)))
        val viewModel = createViewModel()

        viewModel.onEvent(ExploreEvent.QueryChanged("Kaz"))
        advanceTimeBy(200)
        viewModel.onEvent(ExploreEvent.QueryChanged("Kazan"))
        passDebounce()

        coVerify(exactly = 1) { repository.search(any()) }
        coVerify { repository.search("Kazan") }
    }

    @Test
    fun `query is trimmed before searching`() = runTest {
        stubSearch(Result.success(emptyList()))
        val viewModel = createViewModel()

        viewModel.onEvent(ExploreEvent.QueryChanged("  Kazan  "))
        passDebounce()

        coVerify { repository.search("Kazan") }
    }

    @Test
    fun `empty answer is a success with no results`() = runTest {
        stubSearch(Result.success(emptyList()))
        val viewModel = createViewModel()

        viewModel.onEvent(ExploreEvent.QueryChanged("Zzzz"))
        passDebounce()

        assertEquals(SearchStatus.SUCCESS, viewModel.state.value.status)
        assertTrue(viewModel.state.value.results.isEmpty())
    }

    @Test
    fun `failed search shows an error and retry recovers`() = runTest {
        stubSearch(Result.failure(IOException("offline")))
        val viewModel = createViewModel()

        viewModel.onEvent(ExploreEvent.QueryChanged("Kazan"))
        passDebounce()
        assertEquals(SearchStatus.ERROR, viewModel.state.value.status)
        assertTrue(viewModel.state.value.results.isEmpty())

        stubSearch(Result.success(listOf(TestData.kazan)))
        viewModel.onEvent(ExploreEvent.RetryClicked)

        assertEquals(SearchStatus.SUCCESS, viewModel.state.value.status)
        assertEquals(listOf(TestData.kazan), viewModel.state.value.results)
    }

    @Test
    fun `clearing the query drops the results`() = runTest {
        stubSearch(Result.success(listOf(TestData.kazan)))
        val viewModel = createViewModel()
        viewModel.onEvent(ExploreEvent.QueryChanged("Kazan"))
        passDebounce()

        viewModel.onEvent(ExploreEvent.QueryChanged(""))
        passDebounce()

        assertEquals(SearchStatus.IDLE, viewModel.state.value.status)
        assertTrue(viewModel.state.value.results.isEmpty())
    }

    @Test
    fun `clicking a result saves the city and navigates to home`() = runTest {
        val viewModel = createViewModel()

        viewModel.effects.test {
            viewModel.onEvent(ExploreEvent.ResultClicked(TestData.kazan))

            assertEquals(ExploreEffect.NavigateToHome, awaitItem())
        }
        coVerify { repository.select(TestData.kazan) }
    }
}
