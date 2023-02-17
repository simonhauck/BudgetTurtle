package com.github.simonhauck.budgetturtle.commontest.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.simonhauck.budgetturtle.commontest.api.ResourceUtil
import java.io.File
import java.io.FileNotFoundException
import java.nio.charset.StandardCharsets
import org.assertj.core.util.Files
import org.json.JSONObject
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

private val log = mu.KotlinLogging.logger {}

@Component
@Primary
class ResourceUtilImpl(
    private val objectMapper: ObjectMapper,
) : ResourceUtil {

    private var _testClazz: Class<Any>? = null
    val testClazz: Class<Any>
        get() = _testClazz ?: throw Exception("ResourceUtil not initialized")

    override fun init(testClazz: Class<Any>) {
        this._testClazz = testClazz
    }

    override fun read(resourceName: String): String =
        readResource(resourceName).joinToString(System.lineSeparator())

    override fun <T> readAndConvertToObject(resourceName: String, targetClass: Class<T>): T {
        return objectMapper.readValue(read(resourceName), targetClass)
    }

    override fun <T> readAndConvertToList(resourceName: String, targetClass: Class<T>): List<T> {
        val listType =
            objectMapper.typeFactory.constructCollectionType(List::class.java, targetClass)
        return objectMapper.readValue(read(resourceName), listType)
    }

    // This is a helper method to log an object to a console, so it can be copied to a resource file
    override fun logAsJsonObject(something: Any) {
        log.info { "---- Test logged json----" }
        log.info { objectMapper.writeValueAsString(something) }
        log.info { "---- Test logged json end----" }
    }

    override fun readAndNormalizeToJson(resourceName: String): JSONObject =
        JSONObject(readAndNormalize(resourceName))

    override fun readAndNormalize(resourceName: String): String {
        val result = readResource(resourceName)
        return result.map { it.trim() }.joinToString("") { it.replace("\": ", "\":") }
    }

    override fun readFromClassLoader(resourceName: String): String =
        testClazz.classLoader
            .getResourceAsStream(resourceName)
            ?.bufferedReader(StandardCharsets.UTF_8)
            ?.readLines()
            ?.map { it.trim() }
            ?.joinToString("") { it.replace("\": ", "\":") }
            ?: throw Exception("Buffered reader not available")

    override fun readResource(resourceUrl: String): List<String> {
        val url = testClazz.getResource(resourceUrl)
        val filePath =
            url?.file ?: throw FileNotFoundException("File with resource $resourceUrl not found.")
        return Files.linesOf(File(filePath), StandardCharsets.UTF_8)
    }

    override fun readAsByteArray(resourceName: String): ByteArray {
        val url = testClazz.getResource(resourceName)
        val filePath =
            url?.file ?: throw FileNotFoundException("File with resource $resourceName not found.")
        return File(filePath).readBytes()
    }
}
