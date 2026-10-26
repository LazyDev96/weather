package com.example.weather.feature.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable

/** Маршрут вкладки целиком. Вложенный граф нужен, чтобы вкладка оставалась выбранной на подэкранах. */
@Serializable
data object SettingsGraph

@Serializable
internal data object SettingsDestination

@Serializable
internal data object LanguageDestination

@Serializable
internal data object ThemeDestination

/** Вкладка «Настройки»: список настроек и по одному отдельному экрану на язык и тему. */
fun NavGraphBuilder.settingsGraph(navController: NavController) {
    navigation<SettingsGraph>(startDestination = SettingsDestination) {
        composable<SettingsDestination> {
            SettingsRoute(
                onLanguageClick = { navController.navigate(LanguageDestination) },
                onThemeClick = { navController.navigate(ThemeDestination) },
            )
        }
        composable<LanguageDestination> {
            LanguageRoute(onBack = { navController.popBackStack() })
        }
        composable<ThemeDestination> {
            ThemeRoute(onBack = { navController.popBackStack() })
        }
    }
}
