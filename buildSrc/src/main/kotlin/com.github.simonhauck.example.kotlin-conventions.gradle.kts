import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `maven-publish`
    id("jacoco")
    id("org.sonarqube")
    id("com.adarshr.test-logger")
    id("com.coditory.integration-test")
    id("com.ncorti.ktfmt.gradle")
}

// ---------------------------------------------------------------------------------------------------------------------
// Kotlin config
// ---------------------------------------------------------------------------------------------------------------------

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(Constants.JAVA_VERSION))
        this.vendor.set(Constants.JAVA_VENDOR)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "${Constants.JAVA_VERSION}"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

// ---------------------------------------------------------------------------------------------------------------------
// Kotlin formatter
// ---------------------------------------------------------------------------------------------------------------------

ktfmt {
    // KotlinLang style - 4 space indentation - From kotlinlang.org/docs/coding-conventions.html
    kotlinLangStyle()

    blockIndent.set(4)
    continuationIndent.set(4)

    removeUnusedImports.set(true)
}

tasks.register("checkFormat") { dependsOn(tasks.ktfmtCheck) }

tasks.register("format") { dependsOn(tasks.ktfmtFormat) }

// ---------------------------------------------------------------------------------------------------------------------
// Testing config
// ---------------------------------------------------------------------------------------------------------------------

tasks.withType<Test>() {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport { reports { xml.required.set(true) } }

sonarqube {
    properties {
        property("sonar.projectName", "${project.rootProject.name} :: ${project.name}")
        property("sonar.projectKey", "com.bruker.${project.rootProject.name}.${project.name}")
    }
}
