package com.example.weather.core.testing

import java.io.File

/**
 * Строка из composeResources модуля (английская версия, язык по умолчанию в тестах Robolectric).
 *
 * Читает `src/commonMain/composeResources/values/strings.xml` напрямую: рабочая папка JVM-теста Gradle
 * совпадает с папкой модуля. Так тесты ищут элементы по тем же строкам, что показывает экран,
 * а не по копиям текста, и не зависят от загрузки ресурсов Compose Multiplatform.
 * Поддерживает подстановки `%1$d` и `%1$s`.
 */
fun string(name: String, vararg formatArgs: Any): String {
    val file = File("src/commonMain/composeResources/values/strings.xml")
    check(file.exists()) { "Нет ${file.absolutePath}: тест должен запускаться из папки модуля" }
    val raw = Regex("""<string name="${Regex.escape(name)}"[^>]*>(.*?)</string>""", RegexOption.DOT_MATCHES_ALL)
        .find(file.readText())
        ?.groupValues?.get(1)
        ?: error("Строка $name не найдена в ${file.path}")

    val text = raw
        .replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"").replace("&amp;", "&")
        .replace("\\n", "\n").replace("\\'", "'")
    return Regex("""%(\d+)\$[ds]""").replace(text) { match ->
        formatArgs[match.groupValues[1].toInt() - 1].toString()
    }
}
