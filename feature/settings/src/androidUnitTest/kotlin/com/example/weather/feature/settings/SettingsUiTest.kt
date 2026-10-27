package com.example.weather.feature.settings

import com.example.weather.core.data.model.AppLanguage
import com.example.weather.core.data.model.ThemeMode
import org.junit.Assert.assertEquals
import org.junit.Test

/** Каждый вариант выбора должен иметь свою подпись, иначе в списке появятся одинаковые строки. */
class SettingsUiTest {

    @Test
    fun `every language has its own label`() {
        val labels = AppLanguage.entries.map { it.labelRes().key }
        assertEquals(labels.size, labels.toSet().size)
    }

    @Test
    fun `every theme has its own label`() {
        val labels = ThemeMode.entries.map { it.labelRes().key }
        assertEquals(labels.size, labels.toSet().size)
    }
}
