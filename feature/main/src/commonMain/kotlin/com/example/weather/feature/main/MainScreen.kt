package com.example.weather.feature.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weather.feature.explore.exploreScreen
import com.example.weather.feature.favorites.favoritesScreen
import com.example.weather.feature.home.HomeDestination
import com.example.weather.feature.home.homeScreen
import com.example.weather.feature.settings.settingsGraph
import org.jetbrains.compose.resources.stringResource

private const val TAB_FADE_DURATION_MS = 200

/** Главный экран: нижняя навигация и вложенный NavHost для вкладок. */
@Composable
internal fun MainScreen() {
    val tabNavController = rememberNavController()
    val backStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            MainBottomBar(
                isSelected = { tab ->
                    currentDestination?.hierarchy?.any { it.hasRoute(tab.routeClass) } == true
                },
                onTabClick = { tab -> tabNavController.navigateToTab(tab) },
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = HomeDestination,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(tween(TAB_FADE_DURATION_MS)) },
            exitTransition = { fadeOut(tween(TAB_FADE_DURATION_MS)) },
            popEnterTransition = { fadeIn(tween(TAB_FADE_DURATION_MS)) },
            popExitTransition = { fadeOut(tween(TAB_FADE_DURATION_MS)) },
        ) {
            homeScreen()
            exploreScreen(
                onCitySelected = { tabNavController.navigateToTab(TopLevelDestination.HOME) },
            )
            favoritesScreen(
                onLocationSelected = { tabNavController.navigateToTab(TopLevelDestination.HOME) },
            )
            settingsGraph(tabNavController)
        }
    }
}

/** Нижняя навигация без знания о NavController: так её можно проверять отдельно. */
@Composable
internal fun MainBottomBar(
    isSelected: (TopLevelDestination) -> Boolean,
    onTabClick: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        TopLevelDestination.entries.forEach { tab ->
            val selected = isSelected(tab)

            NavigationBarItem(
                selected = selected,
                onClick = { onTabClick(tab) },
                icon = {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(tab.labelRes)) },
            )
        }
    }
}

/** Переключение вкладки с сохранением и восстановлением состояния каждой из них. */
private fun NavController.navigateToTab(tab: TopLevelDestination) {
    navigate(tab.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
