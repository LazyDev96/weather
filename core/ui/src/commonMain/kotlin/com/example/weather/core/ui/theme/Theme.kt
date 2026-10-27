package com.example.weather.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val BrandPrimaryLight = Color(0xFF6750A4)
private val BrandPrimaryDark = Color(0xFFD0BCFF)

private val LightColors = lightColorScheme(primary = BrandPrimaryLight)
private val DarkColors = darkColorScheme(primary = BrandPrimaryDark)

private val AppTypography = Typography()

/** Действующая тема приложения: она может отличаться от системной, если выбрана вручную. */
val LocalDarkTheme = staticCompositionLocalOf { false }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = (if (dynamicColor) dynamicColorScheme(darkTheme) else null)
        ?: if (darkTheme) DarkColors else LightColors

    CompositionLocalProvider(LocalDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content,
        )
    }
}

/** Динамические цвета (Material You) есть только на Android 12+, на остальных платформах null. */
@Composable
internal expect fun dynamicColorScheme(darkTheme: Boolean): ColorScheme?
