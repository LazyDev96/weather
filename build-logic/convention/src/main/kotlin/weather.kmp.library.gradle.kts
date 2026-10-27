import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// Базовый KMP-модуль: Android + iOS, kotlinx.serialization, общие настройки.
plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    compilerOptions {
        // expect/actual классы (в т.ч. сгенерированные Room) пока считаются бета-функцией
        freeCompilerArgs.add("-Xexpect-actual-classes")
        // В kotlinx-datetime 0.7+ Instant и Clock совпадают с kotlin.time.*, а те пока экспериментальные
        optIn.add("kotlin.time.ExperimentalTime")
    }
}

android {
    namespace = weatherNamespace()
    compileSdk = weatherIntProperty("weather.compileSdk")

    defaultConfig {
        minSdk = weatherIntProperty("weather.minSdk")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        // Нужно для Robolectric (Compose UI тесты в androidUnitTest)
        unitTests.isIncludeAndroidResources = true
    }
}
