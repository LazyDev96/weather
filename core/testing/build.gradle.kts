plugins {
    id("weather.android.library")
}

android {
    namespace = "com.example.weather.core.testing"
    compileSdk = providers.gradleProperty("weather.compileSdk").get().toInt()

    defaultConfig {
        minSdk = providers.gradleProperty("weather.minSdk").get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

// Общие зависимости и хелперы для unit-тестов, которые лежат в androidUnitTest:
// testImplementation-аналог — implementation(projects.core.testing)
dependencies {
    api(projects.core.data)

    // Compose UI тесты запускаются на JVM через Robolectric, эмулятор не нужен
    api(libs.androidx.compose.ui.test.junit4)
    api(libs.robolectric)
    api(libs.androidx.test.ext.junit)
    api(libs.androidx.test.core)

    api(libs.junit)
    api(libs.kotlinx.coroutines.test)
    api(libs.mockk)
    api(libs.turbine)
    api(libs.kotlinx.datetime)
}
