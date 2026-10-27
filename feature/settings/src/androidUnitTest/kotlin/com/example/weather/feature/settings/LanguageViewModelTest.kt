package com.example.weather.feature.settings

import app.cash.turbine.test
import com.example.weather.core.data.SettingsRepository
import com.example.weather.core.data.model.AppLanguage
import com.example.weather.core.data.model.AppSettings
import com.example.weather.core.testing.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LanguageViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val settingsFlow = MutableStateFlow(AppSettings())
    private val repository = mockk<SettingsRepository>(relaxUnitFun = true) {
        every { settings } returns settingsFlow
    }

    @Test
    fun `english is selected by default`() = runTest {
        val viewModel = LanguageViewModel(repository)

        assertEquals(AppLanguage.ENGLISH, viewModel.state.value.selected)
    }

    @Test
    fun `selection follows the stored language`() = runTest {
        val viewModel = LanguageViewModel(repository)

        settingsFlow.value = AppSettings(language = AppLanguage.RUSSIAN)

        assertEquals(AppLanguage.RUSSIAN, viewModel.state.value.selected)
    }

    @Test
    fun `choosing a language saves it`() = runTest {
        val viewModel = LanguageViewModel(repository)

        viewModel.onEvent(LanguageEvent.LanguageSelected(AppLanguage.RUSSIAN))

        coVerify { repository.setLanguage(AppLanguage.RUSSIAN) }
    }

    @Test
    fun `back click navigates back`() = runTest {
        val viewModel = LanguageViewModel(repository)

        viewModel.effects.test {
            viewModel.onEvent(LanguageEvent.BackClicked)
            assertEquals(LanguageEffect.NavigateBack, awaitItem())
        }
    }
}
