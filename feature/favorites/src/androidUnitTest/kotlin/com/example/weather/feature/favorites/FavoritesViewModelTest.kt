package com.example.weather.feature.favorites

import app.cash.turbine.test
import com.example.weather.core.data.FavoritesRepository
import com.example.weather.core.data.LocationRepository
import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.testing.MainDispatcherRule
import com.example.weather.core.testing.TestData
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FavoritesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val favoritesFlow = MutableStateFlow<List<WeatherLocation>>(emptyList())
    private val favoritesRepository = mockk<FavoritesRepository>(relaxUnitFun = true) {
        every { observeAll() } returns favoritesFlow
    }
    private val locationRepository = mockk<LocationRepository>(relaxUnitFun = true)

    private fun createViewModel() = FavoritesViewModel(favoritesRepository, locationRepository)

    @Test
    fun `empty list is shown once loading is over`() = runTest {
        val viewModel = createViewModel()

        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.items.isEmpty())
    }

    @Test
    fun `state follows the stored favorites`() = runTest {
        val viewModel = createViewModel()

        favoritesFlow.value = listOf(TestData.kazan, TestData.samara)
        assertEquals(listOf(TestData.kazan, TestData.samara), viewModel.state.value.items)

        favoritesFlow.value = listOf(TestData.samara)
        assertEquals(listOf(TestData.samara), viewModel.state.value.items)
    }

    @Test
    fun `clicking a favorite selects it and asks to leave the screen`() = runTest {
        val viewModel = createViewModel()

        viewModel.effects.test {
            viewModel.onEvent(FavoritesEvent.LocationClicked(TestData.kazan))

            assertEquals(FavoritesEffect.LocationSelected, awaitItem())
        }
        coVerify { locationRepository.select(TestData.kazan) }
    }

    @Test
    fun `removing deletes the favorite by id`() = runTest {
        val viewModel = createViewModel()

        viewModel.onEvent(FavoritesEvent.RemoveClicked(TestData.kazan.id))

        coVerify { favoritesRepository.remove(TestData.kazan.id) }
    }
}
