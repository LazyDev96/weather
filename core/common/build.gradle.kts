plugins {
    id("weather.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // api: наследники MviViewModel в feature-модулях должны видеть ViewModel и корутины
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            // Dispatchers.Main для viewModelScope на Android (на iOS он есть в самих корутинах)
            api(libs.kotlinx.coroutines.android)
        }
    }
}
