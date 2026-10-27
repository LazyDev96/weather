plugins {
    id("weather.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // api: DTO используются в :core:data, а HttpClient и Json — при сборке Koin-графа
            api(libs.ktor.client.core)
            api(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
