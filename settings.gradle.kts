pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "Weather"

// Позволяет писать implementation(projects.core.ui) вместо project(":core:ui")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":androidApp")
include(":shared")

include(":core:common")
include(":core:ui")
include(":core:database")
include(":core:network")
include(":core:data")
include(":core:service")
include(":core:testing")

include(":feature:splash")
include(":feature:main")
include(":feature:home")
include(":feature:explore")
include(":feature:favorites")
include(":feature:settings")
