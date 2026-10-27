// Android-приложение с Compose. Плагины подключены здесь, потому что модулям из включённой сборки
// build-logic нельзя писать id("com.android.application") без версии.
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
}
