package com.example.weather.feature.settings

import app.cash.turbine.test
import com.example.weather.core.data.SettingsRepository
import com.example.weather.core.data.model.AppLanguage
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

class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val settingsFlow = MutableStateFlow(AppSettings())
    private val repository = mockk<SettingsRepository>(relaxUnitFun = true) {
        every { settings } returns settingsFlow
    }

    private fun createViewModel() = SettingsViewModel(repository)

    @Test
    fun `state mirrors stored settings and follows their changes`() = runTest {
        val viewModel = createViewModel()
        assertEquals(AppSettings(), viewModel.state.value.settings)

        val changed = AppSettings(language = AppLanguage.RUSSIAN, theme = ThemeMode.DARK)
        settingsFlow.value = changed

        assertEquals(changed, viewModel.state.value.settings)
    }

    @Test
    fun `language click opens the language screen`() = runTest {
        val viewModel = createViewModel()

        viewModel.effects.test {
            viewModel.onEvent(SettingsEvent.LanguageClicked)
            assertEquals(SettingsEffect.OpenLanguage, awaitItem())
        }
    }

    @Test
    fun `theme click opens the theme screen`() = runTest {
        val viewModel = createViewModel()

        viewModel.effects.test {
            viewModel.onEvent(SettingsEvent.ThemeClicked)
            assertEquals(SettingsEffect.OpenTheme, awaitItem())
        }
    }

    @Test
    fun `toggling the weather notification is saved`() = runTest {
        val viewModel = createViewModel()

        viewModel.onEvent(SettingsEvent.WeatherNotificationToggled(false))

        coVerify { repository.setWeatherNotificationEnabled(false) }
    }
}
