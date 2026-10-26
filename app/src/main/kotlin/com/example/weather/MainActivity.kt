package com.example.weather

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weather.core.data.model.AppLanguage
import com.example.weather.core.data.model.AppSettings
import com.example.weather.core.data.model.ThemeMode
import com.example.weather.core.service.WeatherForegroundService
import com.example.weather.core.ui.theme.AppTheme
import com.example.weather.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

private val LightScrim = Color.argb(0xE6, 0xFF, 0xFF, 0xFF)
private val DarkScrim = Color.argb(0x80, 0x1B, 0x1B, 0x1B)

// AppCompatActivity нужна, чтобы AppCompatDelegate менял язык на Android ниже 13.
// Сама разметка при этом остаётся на Compose.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    // Ответ не обрабатываем: без разрешения служба работает, просто не показывает уведомление
    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Системный splash показывает только цвет фона (иконка прозрачная), картинку рисует
        // Compose-экран :feature:splash. Держим его, пока не загрузятся настройки,
        // чтобы тема применилась с первого кадра, без мигания.
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition { viewModel.state.value.settings == null }
        observeLanguage()
        observeWeatherNotification()

        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val settings = state.settings ?: AppSettings()

            val darkTheme = when (settings.theme) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            // Иконки статус-бара и навигации должны быть читаемы в выбранной теме,
            // даже если она отличается от системной
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(LightScrim, DarkScrim) { darkTheme },
                )
                onDispose { }
            }

            AppTheme(darkTheme = darkTheme) {
                AppNavHost()
            }
        }
    }

    /**
     * Применяет язык из настроек. AppCompat пересоздаёт Activity только если язык
     * действительно изменился: при обычном запуске повторного пересоздания нет.
     */
    private fun observeLanguage() {
        lifecycleScope.launch {
            viewModel.state
                .mapNotNull { it.settings?.language }
                .distinctUntilChanged()
                .collect(::applyLanguage)
        }
    }

    /**
     * Запускает и останавливает службу с уведомлением о погоде по настройке. Запуск только
     * пока приложение на экране (STARTED): из фона Android 12+ не разрешает стартовать
     * foreground service. Уже запущенная служба продолжает работать и после ухода в фон.
     */
    private fun observeWeatherNotification() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .mapNotNull { it.settings?.weatherNotificationEnabled }
                    .distinctUntilChanged()
                    .collect { enabled ->
                        if (enabled) {
                            requestNotificationPermissionIfNeeded()
                            WeatherForegroundService.start(this@MainActivity)
                        } else {
                            WeatherForegroundService.stop(this@MainActivity)
                        }
                    }
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        val granted = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED
        if (!granted) notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    private fun applyLanguage(language: AppLanguage) {
        val currentLanguage = AppCompatDelegate.getApplicationLocales()[0]?.language
        if (currentLanguage != language.tag) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language.tag))
        }
    }
}
