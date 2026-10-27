plugins {
    `kotlin-dsl`
}

// Gradle 8.10 компилирует convention-плагины своим встроенным Kotlin 1.9, а плагины Compose и KGP
// собраны Kotlin 2.x. Метаданные читаются нормально, поэтому проверку версии отключаем.
// Если обновите Gradle до 8.14 и новее (встроенный Kotlin 2.0+), флаг можно убрать.
kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xskip-metadata-version-check")
    }
}

dependencies {
    // Плагины, которые применяют convention-плагины. Сами модули берут их без указания версии.
    implementation(libs.gradle.plugin.android)
    implementation(libs.gradle.plugin.kotlin)
    implementation(libs.gradle.plugin.kotlin.compose)
    implementation(libs.gradle.plugin.kotlin.serialization)
    implementation(libs.gradle.plugin.compose)
    implementation(libs.gradle.plugin.ksp)
}
