import org.gradle.api.Project

/** `:feature:home` -> `com.example.weather.feature.home`, `:core:ui` -> `com.example.weather.core.ui`. */
fun Project.weatherNamespace(): String =
    "com.example.weather." + path.removePrefix(":").replace(':', '.').replace('-', '_')

fun Project.weatherIntProperty(name: String): Int =
    providers.gradleProperty(name).get().toInt()
