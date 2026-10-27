package com.example.weather.core.data.model

/** Языки интерфейса. По умолчанию английский, независимо от языка системы. */
enum class AppLanguage(val tag: String) {
    RUSSIAN("ru"),
    ENGLISH("en"),
    ;

    companion object {
        val Default = ENGLISH

        fun fromTag(tag: String?): AppLanguage = entries.firstOrNull { it.tag == tag } ?: Default
    }
}

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM,
    ;

    companion object {
        val Default = SYSTEM

        fun fromName(name: String?): ThemeMode = entries.firstOrNull { it.name == name } ?: Default
    }
}

data class AppSettings(
    val language: AppLanguage = AppLanguage.Default,
    val theme: ThemeMode = ThemeMode.Default,
    /** Постоянное уведомление с погодой (foreground service). */
    val weatherNotificationEnabled: Boolean = true,
)
