package com.example.weather.feature.explore

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val exploreModule = module {
    viewModelOf(::ExploreViewModel)
}
