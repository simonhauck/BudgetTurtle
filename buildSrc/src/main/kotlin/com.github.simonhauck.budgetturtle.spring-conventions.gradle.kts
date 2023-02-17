plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

springBoot {
    // If the time always changes, the task can not be cached
    buildInfo {
        excludes.set(listOf("time"))
    }
}

// Mark classes annotated with those as open for jpa
allOpen {
    annotations(
        "jakarta.persistence.Entity",
        "jakarta.persistence.MappedSuperclass",
        "jakarta.persistence.Embeddable",
        "org.springframework.boot.autoconfigure.SpringBootApplication",
    )
}