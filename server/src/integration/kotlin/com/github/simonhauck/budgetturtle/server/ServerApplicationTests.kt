package com.github.simonhauck.budgetturtle.server

import org.junit.jupiter.api.Test

class ServerApplicationTests : IntegrationTest() {

    @Test fun contextLoads() {}

    @Test
    fun `check that the open api json from server-api is identical to openApiJson by swagger`() =
        testDriver(this) {
            val swaggerOpenApiJson = get("/v3/api-docs/openapi.json")

            val openApiJsonFromBeApi = readFromClassLoader("openapi.json")

            swaggerOpenApiJson.body.assertJsonEquals(openApiJsonFromBeApi, "servers[*].url")
        }
}
