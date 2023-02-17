plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
    mavenCentral()
}

dependencies {
    // Docker container
    implementation("com.google.cloud.tools:jib-gradle-plugin:3.3.1")

    // Sonar
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:4.0.0.2929")

    // Add integration tests
    implementation("com.coditory.gradle:integration-test-plugin:1.4.5")

    // Print out test results nicely
    implementation("com.adarshr:gradle-test-logger-plugin:3.2.0")

    // Kotlin version
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")

    // Kotlin formatting
    implementation("com.ncorti.ktfmt.gradle:plugin:0.11.0")

    // Spring plugins
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.0.2")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.0")
    implementation("org.jetbrains.kotlin:kotlin-noarg:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.8.10")
}
