plugins {
    id("weather.kmp.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.data)
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
