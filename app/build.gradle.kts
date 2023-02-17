import org.gradle.internal.os.OperatingSystem
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("com.github.simonhauck.budgetturtle.artifactory")
    id("org.openapi.generator") version "6.3.0"
}

val apiSpecification: Configuration by configurations.creating {}

dependencies { apiSpecification(project(":server-api", "json")) }

// ---------------------------------------------------------------------------------------------------------------------
// Generate flutter binding
// ---------------------------------------------------------------------------------------------------------------------

val apiBindingBaseFolder = "$buildDir/api-binding"
val openApiDirectory = "$apiBindingBaseFolder/specification"
val clientCode = "$apiBindingBaseFolder/client"

openApiGenerate {
    generatorName.set("dart-dio")
    inputSpec.set("$openApiDirectory/openapi.json")
    outputDir.set(clientCode)

    configOptions.set(
        mapOf(
            "pubAuthor" to "Spring Fullstack Example App",
            "pubAuthorEmail" to "example@gmx.de",
            "pubDescription" to "Flutter api binding for the server application",
            "pubVersion" to "1.0.0",
            "pubName" to "server",
            "pubLibrary" to "server.api",
        )
    )
}

val copyApiBindingTask =
    tasks.register<Copy>("copyApiBinding") {
        dependsOn(apiSpecification)
        performCachedCopy(openApiDirectory, apiSpecification.files)
    }

val generateFlutterBindingTask =
    tasks.withType<GenerateTask> {
        dependsOn(copyApiBindingTask)
        inputs.files(openApiDirectory)
        outputs.dir(clientCode)
        doFirst {
            println("Deleting api binding in $clientCode$")
            delete(clientCode)
        }

        doLast {
            runFlutterCommand("flutter pub get", clientCode)
            runFlutterCommand("flutter pub run build_runner build", clientCode)
            runDartCommand("dart format .", clientCode)
        }
    }

val installPubDependenciesTask =
    tasks.register("installAppDependencies") {
        dependsOn(generateFlutterBindingTask)
        inputs.files("pubspec.yaml", clientCode)
        outputs.files("pubspec.lock")

        doLast { runFlutterCommand("flutter pub get") }
    }

val prepareEnvTask = tasks.register("prepareEnv") { dependsOn(installPubDependenciesTask) }

// ---------------------------------------------------------------------------------------------------------------------
// Build App
// ---------------------------------------------------------------------------------------------------------------------

val buildReleaseApkTask =
    tasks.register("buildReleaseApk") {
        dependsOn(prepareEnvTask)
        val versionTag = "--build-name=${project.version}"
        doLast { runFlutterCommand("flutter build apk --release $versionTag") }
    }

// ---------------------------------------------------------------------------------------------------------------------
// Build Web
// ---------------------------------------------------------------------------------------------------------------------

val flutterWebBuildDir = "$buildDir/web"

val buildWebReleaseTask =
    tasks.register("buildWebRelease") {
        dependsOn(prepareEnvTask)
        doLast {
            val versionTag = "--build-name=${project.version}"
            runFlutterCommand("flutter build web --web-renderer canvaskit $versionTag")
        }
    }

val zipWebReleaseTask =
    tasks.register<Zip>("zipWebRelease") {
        dependsOn(buildWebReleaseTask)
        from(flutterWebBuildDir)
        destinationDirectory.set(file("$buildDir/zip"))
        archiveBaseName.set("flutter-web")
    }

tasks.register("assemble") { dependsOn(buildReleaseApkTask, zipWebReleaseTask) }

val webDistZipConfig = configurations.create("default")

artifacts.add(webDistZipConfig.name, zipWebReleaseTask.get())

// ---------------------------------------------------------------------------------------------------------------------
// Prepare Release
// ---------------------------------------------------------------------------------------------------------------------

tasks.register<Copy>("prepareReleaseArtifacts") {
    dependsOn(buildReleaseApkTask)
    performCachedCopy(
        "${rootProject.buildDir}/artifacts/${project.name}",
        setOf("$buildDir/${project.name}/outputs/flutter-apk")
    )
    rename { "${rootProject.name}-$it" }
}

// ---------------------------------------------------------------------------------------------------------------------
// Test tasks
// ---------------------------------------------------------------------------------------------------------------------

val flutterTestTask =
    tasks.register("test") {
        dependsOn(prepareEnvTask)
        doLast { runFlutterCommand("flutter test") }
    }

tasks.register("integrationTest") {
    dependsOn(prepareEnvTask)
    // for now just run flutter analyze
    doLast { runFlutterCommand("flutter analyze .") }
}

tasks.register("format") {
    dependsOn(prepareEnvTask)
    doLast { runDartCommand("dart format .") }
}

tasks.register("checkFormat") {
    dependsOn(prepareEnvTask)
    doLast { runDartCommand("dart format . --set-exit-if-changed") }
}

// ---------------------------------------------------------------------------------------------------------------------
// Utility
// ---------------------------------------------------------------------------------------------------------------------

tasks.register("clean") { runFlutterCommand("flutter clean") }

fun runFlutterCommand(flutterCommand: String, workDir: String = projectDir.absolutePath) {
    exec {
        val command = flutterCommand.toOsCommand()
        println("Executing $command in $workDir")
        environment = System.getenv().toMap()
        workingDir = file(workDir)
        commandLine = command
    }
}

fun runDartCommand(flutterCommand: String, workDir: String = projectDir.absolutePath) {
    exec {
        val command = flutterCommand.toOsCommand()
        println("Executing $command in $workDir")
        environment = System.getenv().toMap()
        workingDir = file(workDir)
        commandLine = command
    }
}

fun String.toOsCommand(): List<String> {
    return if (OperatingSystem.current().isWindows) listOf("cmd.exe", "/c", this)
    else this.split(" ").toList()
}

fun Copy.performCachedCopy(target: String, inputFiles: Set<Any>) {
    inputs.files(inputFiles)
    outputs.dir(target)
    doFirst { delete(target) }
    from(inputFiles)
    into(target)
}
