plugins {
    java
    id("jacoco")
}

// Java Config
java {
    toolchain {
        this.languageVersion.set(JavaLanguageVersion.of(Constants.JAVA_VERSION))
        this.vendor.set(Constants.JAVA_VENDOR)
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}


