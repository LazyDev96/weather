plugins {
    id("weather.kmp.compose")
}

kotlin {
    // Единый iOS-фреймворк: Xcode-проект подключает только его (см. iosApp/project.yml).
    // Именно binaries.framework создаёт бинарник и задачу embedAndSignAppleFrameworkForXcode.
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
        binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // api: MainViewModel и App() видны из androidApp, их типы (MviViewModel, AppSettings) должны быть на его classpath
            api(projects.core.common)
            api(projects.core.data)
            implementation(projects.core.ui)
            implementation(projects.core.network)
            implementation(projects.core.database)
            implementation(projects.feature.splash)
            implementation(projects.feature.main)
            implementation(projects.feature.home)
            implementation(projects.feature.explore)
            implementation(projects.feature.favorites)
            implementation(projects.feature.settings)
            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
        }
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}
