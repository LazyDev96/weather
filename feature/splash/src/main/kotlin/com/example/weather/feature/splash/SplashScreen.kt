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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather.core.ui.mvi.CollectEffects
import com.example.weather.core.ui.theme.AppTheme

@Composable
internal fun SplashRoute(
    onFinished: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
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
            .background(colorResource(R.color.splash_background)),
        contentAlignment = Alignment.Center,
    ) {
        // Логотип лежит в drawable/splash_logo.xml; свою картинку (PNG/WebP/вектор) кладите под тем же именем.
        Image(
            painter = painterResource(R.drawable.splash_logo),
            contentDescription = stringResource(R.string.splash_image_description),
            modifier = Modifier.graphicsLayer {
                alpha = progress.value
                val scale = 0.9f + 0.1f * progress.value
                scaleX = scale
                scaleY = scale
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    AppTheme { SplashScreen() }
}
