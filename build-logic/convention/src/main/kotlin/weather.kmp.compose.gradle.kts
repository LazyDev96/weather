// KMP-модуль с Compose Multiplatform. Строки и картинки лежат в src/commonMain/composeResources,
// класс Res генерируется (always: auto не срабатывает, если зависимость на ресурсы приходит транзитивно через core:ui) в пакете `<namespace>.resources` и виден только внутри модуля.
plugins {
    id("weather.kmp.library")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
}

compose.resources {
    publicResClass = false
    packageOfResClass = weatherNamespace() + ".resources"
    generateResClass = always
}
