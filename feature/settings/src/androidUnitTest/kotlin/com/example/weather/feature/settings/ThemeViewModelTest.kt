package com.example.weather.feature.settings

import app.cash.turbine.test
import com.example.weather.core.data.SettingsRepository
import com.example.weather.core.data.model.AppSettings
import com.example.weather.core.data.model.ThemeMode
import com.example.weather.core.testing.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ThemeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val settingsFlow = MutableStateFlow(AppSettings())
    private val repository = mockk<SettingsRepository>(relaxUnitFun = true) {
        every { settings } returns settingsFlow
    }

    @Test
    fun `system theme is selected by default`() = runTest {
        val viewModel = ThemeViewModel(repository)

        assertEquals(ThemeMode.SYSTEM, viewModel.state.value.selected)
    }

    @Test
    fun `selection follows the stored theme`() = runTest {
        val viewModel = ThemeViewModel(repository)

        settingsFlow.value = AppSettings(theme = ThemeMode.DARK)

        assertEquals(ThemeMode.DARK, viewModel.state.value.selected)
    }

    @Test
    fun `choosing a theme saves it`() = runTest {
        val viewModel = ThemeViewModel(repository)

        viewModel.onEvent(ThemeEvent.ThemeSelected(ThemeMode.LIGHT))

        coVerify { repository.setTheme(ThemeMode.LIGHT) }
    }

    @Test
    fun `back click navigates back`() = runTest {
        val viewModel = ThemeViewModel(repository)

        viewModel.effects.test {
            viewModel.onEvent(ThemeEvent.BackClicked)
            assertEquals(ThemeEffect.NavigateBack, awaitItem())
        }
    }
}
