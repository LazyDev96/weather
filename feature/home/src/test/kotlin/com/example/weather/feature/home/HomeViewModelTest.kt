package com.example.weather.feature.home

import app.cash.turbine.test
import com.example.weather.core.data.FavoritesRepository
import com.example.weather.core.data.LocationRepository
import com.example.weather.core.data.WeatherRepository
import com.example.weather.core.data.model.Weather
import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.testing.MainDispatcherRule
import com.example.weather.core.testing.TestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val selectedFlow = MutableStateFlow(TestData.moscow)
    private val moscowWeather = MutableStateFlow<Weather?>(null)
    private val kazanWeather = MutableStateFlow<Weather?>(null)
    private val favoriteFlow = MutableStateFlow(false)

    private val locationRepository = mockk<LocationRepository>(relaxUnitFun = true) {
        every { observeSelected() } returns selectedFlow
    }
    private val weatherRepository = mockk<WeatherRepository> {
        every { observe(TestData.moscow) } returns moscowWeather
        every { observe(TestData.kazan) } returns kazanWeather
        coEvery { refresh(any(), any()) } returns Result.success(Unit)
    }
    private val favoritesRepository = mockk<FavoritesRepository>(relaxUnitFun = true) {
        every { observeIsFavorite(any()) } returns favoriteFlow
    }

    private fun createViewModel() =
        HomeViewModel(weatherRepository, locationRepository, favoritesRepository)

    private fun failRefresh() {
        coEvery { weatherRepository.refresh(any(), any()) } returns Result.failure(IOException("offline"))
    }

    @Test
    fun `loads the selected city on start without forcing the network`() = runTest {
        createViewModel()

        coVerify { weatherRepository.refresh(TestData.moscow, false) }
    }

    @Test
    fun `shows cached weather as soon as it is available`() = runTest {
        val viewModel = createViewModel()
        assertNull(viewModel.state.value.weather)

        val weather = TestData.weather(TestData.moscow)
        moscowWeather.value = weather

        assertEquals(weather, viewModel.state.value.weather)
        assertFalse(viewModel.state.value.isRefreshing)
        assertFalse(viewModel.state.value.hasError)
    }

    @Test
    fun `failed load without cache shows the error state`() = runTest {
        failRefresh()

        val viewModel = createViewModel()

        assertTrue(viewModel.state.value.hasError)
        assertFalse(viewModel.state.value.isRefreshing)
        assertNull(viewModel.state.value.weather)
    }

    @Test
    fun `background load failure does not send a message to the user`() = runTest {
        failRefresh()
        val viewModel = createViewModel()

        viewModel.effects.test {
            expectNoEvents()
        }
    }

    @Test
    fun `refresh click forces the network and reports success silently`() = runTest {
        val viewModel = createViewModel()

        viewModel.effects.test {
            viewModel.onEvent(HomeEvent.RefreshClicked)

            expectNoEvents()
        }
        coVerify { weatherRepository.refresh(TestData.moscow, true) }
        assertFalse(viewModel.state.value.hasError)
    }

    @Test
    fun `failed manual refresh notifies the user and keeps cached data`() = runTest {
        val viewModel = createViewModel()
        val weather = TestData.weather(TestData.moscow)
        moscowWeather.value = weather
        failRefresh()

        viewModel.effects.test {
            viewModel.onEvent(HomeEvent.RefreshClicked)

            assertEquals(HomeEffect.RefreshFailed, awaitItem())
        }
        assertEquals(weather, viewModel.state.value.weather)
    }

    @Test
    fun `switching the city switches the shown weather`() = runTest {
        val viewModel = createViewModel()
        moscowWeather.value = TestData.weather(TestData.moscow, temperature = 5.0)
        kazanWeather.value = TestData.weather(TestData.kazan, temperature = 1.0)

        selectedFlow.value = TestData.kazan

        assertEquals(TestData.kazan, viewModel.state.value.weather?.location)
        coVerify { weatherRepository.refresh(TestData.kazan, false) }
    }

    @Test
    fun `refresh after switching the city targets the new city`() = runTest {
        val viewModel = createViewModel()
        selectedFlow.value = TestData.kazan

        viewModel.onEvent(HomeEvent.RefreshClicked)

        coVerify { weatherRepository.refresh(TestData.kazan, true) }
    }

    @Test
    fun `favorite flag follows the repository`() = runTest {
        val viewModel = createViewModel()
        assertFalse(viewModel.state.value.isFavorite)

        favoriteFlow.value = true

        assertTrue(viewModel.state.value.isFavorite)
    }

    @Test
    fun `favorite click adds the city when it is not a favorite yet`() = runTest {
        val viewModel = createViewModel()

        viewModel.onEvent(HomeEvent.FavoriteClicked)

        coVerify { favoritesRepository.add(TestData.moscow) }
        coVerify(exactly = 0) { favoritesRepository.remove(any()) }
    }

    @Test
    fun `favorite click removes the city when it is already a favorite`() = runTest {
        val viewModel = createViewModel()
        favoriteFlow.value = true

        viewModel.onEvent(HomeEvent.FavoriteClicked)

        coVerify { favoritesRepository.remove(TestData.moscow.id) }
        coVerify(exactly = 0) { favoritesRepository.add(any<WeatherLocation>()) }
    }
}
