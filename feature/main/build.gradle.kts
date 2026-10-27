plugins {
    id("weather.kmp.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            // Контейнер знает обо всех вкладках, сами вкладки друг о друге не знают
            implementation(projects.feature.home)
            implementation(projects.feature.explore)
            implementation(projects.feature.favorites)
            implementation(projects.feature.settings)
            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
        }
        named("androidUnitTest") {
            dependencies {
                implementation(projects.core.testing)
            }
        }
    }
}

dependencies {
    // Манифест с ComponentActivity для Compose UI тестов
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
