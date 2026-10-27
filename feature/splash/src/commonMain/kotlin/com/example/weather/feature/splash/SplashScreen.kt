package com.example.weather.feature.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.weather.core.ui.mvi.CollectEffects
import com.example.weather.core.ui.theme.AppTheme
import com.example.weather.feature.splash.resources.Res
import com.example.weather.feature.splash.resources.splash_image_description
import com.example.weather.feature.splash.resources.splash_logo
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.ui.graphics.Color
import com.example.weather.core.ui.theme.LocalDarkTheme

@Composable
internal fun SplashRoute(
    onFinished: () -> Unit,
    viewModel: SplashViewModel = koinViewModel(),
) {
    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            SplashEffect.NavigateToMain -> onFinished()
        }
    }

    SplashScreen()
}

@Composable
internal fun SplashScreen(modifier: Modifier = Modifier) {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(splashBackground()),
        contentAlignment = Alignment.Center,
    ) {
        // Логотип лежит в drawable/splash_logo.xml; свою картинку (PNG/WebP/вектор) кладите под тем же именем.
        Image(
            painter = painterResource(Res.drawable.splash_logo),
            contentDescription = stringResource(Res.string.splash_image_description),
            modifier = Modifier.graphicsLayer {
                alpha = progress.value
                val scale = 0.9f + 0.1f * progress.value
                scaleX = scale
                scaleY = scale
            },
        )
    }
}

// Цвет совпадает с фоном нативного сплэша (Android: themes.xml, iOS: LaunchScreen), поэтому перехода не видно
@Composable
private fun splashBackground(): Color =
    if (LocalDarkTheme.current) Color(0xFF1C1B1F) else Color(0xFFFFFFFF)

@Preview
@Composable
private fun SplashScreenPreview() {
    AppTheme { SplashScreen() }
}
