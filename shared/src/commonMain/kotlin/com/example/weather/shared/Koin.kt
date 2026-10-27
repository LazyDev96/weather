package com.example.weather.shared

import com.example.weather.core.data.dataModule
import com.example.weather.core.database.databaseModule
import com.example.weather.core.network.networkModule
import com.example.weather.feature.explore.exploreModule
import com.example.weather.feature.favorites.favoritesModule
import com.example.weather.feature.home.homeModule
import com.example.weather.feature.settings.settingsModule
import com.example.weather.feature.splash.splashModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val appModule = module {
    viewModelOf(::MainViewModel)
}

/** Единственная точка сборки графа зависимостей; платформы передают свои настройки в [appDeclaration]. */
fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication = startKoin {
    appDeclaration()
    modules(
        networkModule,
        databaseModule,
        dataModule,
        appModule,
        splashModule,
        homeModule,
        exploreModule,
        favoritesModule,
        settingsModule,
    )
}
