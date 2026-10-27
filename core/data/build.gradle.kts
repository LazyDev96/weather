plugins {
    id("weather.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.network)
            implementation(projects.core.database)

            // api: модели с датами используют kotlinx-datetime, DataStore торчит из Koin-модуля
            api(libs.kotlinx.datetime)
            api(libs.androidx.datastore.preferences.core)
            api(libs.kotlinx.coroutines.core)
            implementation(libs.okio)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}
