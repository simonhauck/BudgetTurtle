allprojects {
    group = "com.github.simonhauck.full-stack-spring-example"
    version = readVersionFromFile()
}

fun readVersionFromFile(): String {
    val versionPropertiesFile = "${rootDir.absolutePath}/version.properties"
    val fis = java.io.FileInputStream(versionPropertiesFile)
    val prop = java.util.Properties()
    prop.load(fis)

    return prop.getProperty("version")
}
