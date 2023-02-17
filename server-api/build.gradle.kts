val zipConfig = configurations.create("json")

artifacts.add(zipConfig.name, file("$projectDir/src/main/resources/openapi.json"))
