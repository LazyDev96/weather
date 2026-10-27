plugins {
    id("weather.kmp.room")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // api: Room-типы (RoomDatabase) торчат из Koin-модуля и сигнатур DAO
            api(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.koin.core)
            api(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}

// Схемы базы экспортируются для проверки миграций
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

// Room генерирует код отдельно для каждой цели
dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
}
