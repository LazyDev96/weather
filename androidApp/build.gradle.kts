plugins {
    id("weather.android.application")
}

android {
    namespace = "com.example.weather"
    compileSdk = providers.gradleProperty("weather.compileSdk").get().toInt()

    defaultConfig {
        applicationId = "com.example.weather"
        minSdk = providers.gradleProperty("weather.minSdk").get().toInt()
        targetSdk = providers.gradleProperty("weather.targetSdk").get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            // Для релиза включите minify и shrinkResources и проверьте правила R8
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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

dependencies {
    implementation(projects.shared)
    implementation(projects.core.data)
    implementation(projects.core.service)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(compose.runtime)
}
