package com.example.weather.feature.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home as HomeOutlined
import androidx.compose.material.icons.outlined.Settings as SettingsOutlined
import androidx.compose.material.icons.outlined.Search as SearchOutlined
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.weather.feature.explore.ExploreDestination
import com.example.weather.feature.favorites.FavoritesDestination
import com.example.weather.feature.home.HomeDestination
import com.example.weather.feature.settings.SettingsGraph
import kotlin.reflect.KClass

/** Вкладки нижней навигации. Порядок элементов = порядок на экране. */
enum class TopLevelDestination(
    val route: Any,
    @StringRes val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    HOME(
        route = HomeDestination,
        labelRes = R.string.main_tab_home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.HomeOutlined,
    ),
    EXPLORE(
        route = ExploreDestination,
        labelRes = R.string.main_tab_explore,
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.SearchOutlined,
    ),
    FAVORITES(
        route = FavoritesDestination,
        labelRes = R.string.main_tab_favorites,
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
    ),
    SETTINGS(
        route = SettingsGraph,
        labelRes = R.string.main_tab_settings,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.SettingsOutlined,
    );

    val routeClass: KClass<*> get() = route::class
}
