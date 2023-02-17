rootProject.name = "full-stack-spring-example"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

include("server")

include("server-api")

include("common-test")

include("app")
