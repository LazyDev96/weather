plugins {
    id("weather.android.library")
}

// Служба уведомления существует только на Android, поэтому модуль не мультиплатформенный
android {
    namespace = "com.example.weather.core.service"
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
        optIn.add("kotlin.time.ExperimentalTime")
    }
}

dependencies {
    implementation(projects.core.data)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.koin.android)
    implementation(libs.kotlinx.datetime)
}
