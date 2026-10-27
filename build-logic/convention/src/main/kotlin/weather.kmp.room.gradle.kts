// KMP-модуль с Room: KSP-процессор.
// Gradle-плагин androidx.room не используется: он собран Kotlin 2.1, и встроенный в Gradle компилятор
// не может прочитать его метаданные при генерации аксессоров. Схема экспортируется аргументом KSP.
plugins {
    id("weather.kmp.library")
    id("com.google.devtools.ksp")
}
