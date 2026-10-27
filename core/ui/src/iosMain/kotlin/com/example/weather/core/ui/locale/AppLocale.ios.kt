package com.example.weather.core.ui.locale

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.preferredLanguages

actual object LocalAppLocale {
    private val default = NSLocale.preferredLanguages.first() as String
    private val LocalIosLocale = staticCompositionLocalOf { default }

    actual val current: String
        @Composable get() = LocalIosLocale.current

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        val new = value ?: default
        if (value == null) {
            NSUserDefaults.standardUserDefaults.removeObjectForKey("AppleLanguages")
        } else {
            NSUserDefaults.standardUserDefaults.setObject(listOf(new), "AppleLanguages")
        }
        return LocalIosLocale.provides(new)
    }
}
