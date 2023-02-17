plugins { id("com.google.cloud.tools.jib") }

jib {
    to {
        val registryPrefix = System.getenv("DOCKER_REGISTRY")?.plus("/") ?: ""
        val branchName = System.getenv("BRANCH_NAME") ?: "unknown"

        image = "$registryPrefix${project.rootProject.name}-${project.name}"
        tags = setOf("${project.version}", branchName)
        auth {
            username = System.getenv("DOCKER_USR")
            password = System.getenv("DOCKER_PSW")
        }
    }
}
