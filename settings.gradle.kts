rootProject.name = "budget-turtle"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

include(
    "server",
    "server-api",
    "common-test",
    "app"
)
