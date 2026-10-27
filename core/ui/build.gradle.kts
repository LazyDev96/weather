plugins {
    id("weather.kmp.compose")
}

// Все Compose-, Koin- и lifecycle-зависимости отдаём через api, чтобы feature-модулям
// не нужно было повторять их в каждом build.gradle.kts.
kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)

            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(libs.compose.material.icons.core)
            api(libs.jetbrains.lifecycle.runtime.compose)

            api(libs.koin.core)
            api(libs.koin.core.viewmodel)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}
