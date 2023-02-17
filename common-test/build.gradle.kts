plugins {
    id("com.github.simonhauck.example.artifactory")
    id("com.github.simonhauck.example.kotlin-conventions")
    id("com.github.simonhauck.example.spring-conventions")
}

// Dont generate a bootJar
the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

tasks.bootJar {
    enabled = false
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    // Test Utility
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    implementation("org.xmlunit:xmlunit-assertj:2.9.1")
    implementation("org.awaitility:awaitility:4.2.0")
}
