package com.example.weather.feature.splash

import app.cash.turbine.test
import com.example.weather.core.testing.MainDispatcherRule
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `starts in loading state`() = runTest {
        val viewModel = SplashViewModel()

        assertTrue(viewModel.state.value.isLoading)
    }

    @Test
    fun `does not navigate before the splash delay has passed`() = runTest {
        val viewModel = SplashViewModel()

        viewModel.effects.test {
            advanceTimeBy(1_400)
            runCurrent()

            expectNoEvents()
            assertTrue(viewModel.state.value.isLoading)
        }
    }

    @Test
    fun `navigates to main and stops loading after the delay`() = runTest {
        val viewModel = SplashViewModel()

        viewModel.effects.test {
            advanceTimeBy(1_600)
            runCurrent()

            assertEquals(SplashEffect.NavigateToMain, awaitItem())
        }
        assertFalse(viewModel.state.value.isLoading)
    }
}
