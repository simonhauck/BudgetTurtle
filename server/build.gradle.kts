import org.springdoc.openapi.gradle.plugin.OpenApiGeneratorTask

plugins {
    id("com.github.simonhauck.budgetturtle.artifactory")
    id("com.github.simonhauck.budgetturtle.kotlin-conventions")
    id("com.github.simonhauck.budgetturtle.spring-conventions")
    id("com.github.simonhauck.budgetturtle.docker")

    // Generate open api doc
    id("org.springdoc.openapi-gradle-plugin") version "1.6.0"
}

val apiSpecification: Configuration by configurations.creating {}
val staticFrontendResources: Configuration by configurations.creating {}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    //    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // KMongo for database
    implementation("org.litote.kmongo:kmongo:4.8.0")

    // Handle errors
    implementation("io.arrow-kt:arrow-core:1.1.5")

    // OpenApi Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    // Logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    // Csv parsing
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.8.0")

    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(project(":common-test"))

    apiSpecification(project(":server-api", "json"))

    staticFrontendResources(project(":app"))
}

tasks.processResources {
    dependsOn(apiSpecification)
    from(apiSpecification) { into("") }

    // To add frontend add '-Pflutter' as argument to the gradle command
    if (project.properties.containsKey("flutter")) {
        dependsOn(staticFrontendResources)
        from(zipTree(staticFrontendResources.singleFile)) { into("static") }
    }
}

jib { container { ports = listOf("8080") } }

// ---------------------------------------------------------------------------------------------------------------------
// Open Api Generation
// ---------------------------------------------------------------------------------------------------------------------

// User another port to have it not clashing with running instances
openApi {
    val apiGeneratedPort = 59186
    apiDocsUrl.set("http://localhost:$apiGeneratedPort/v3/api-docs/openapi.json")
    outputDir.set(file("${project(":server-api").projectDir}/src/main/resources"))

    customBootRun {
        args.set(
            listOf(
                "--server.port=$apiGeneratedPort",
            )
        )
    }
}

tasks.withType<OpenApiGeneratorTask>() { outputs.upToDateWhen { false } }

tasks.withType<com.github.psxpaul.task.JavaExecFork> {
    dependsOn(tasks.getByName("inspectClassesForKotlinIC"))
}
