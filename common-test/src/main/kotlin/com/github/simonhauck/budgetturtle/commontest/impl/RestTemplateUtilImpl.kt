package com.github.simonhauck.budgetturtle.commontest.impl

import com.github.simonhauck.budgetturtle.commontest.api.RestTemplateUtil
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Primary
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.stereotype.Component

@Component
@Primary
class RestTemplateUtilImpl(
    private val restTemplate: TestRestTemplate,
) : RestTemplateUtil {

    val defaultHeaders = HttpHeaders().also { it.contentType = MediaType.APPLICATION_JSON }

    override fun get(url: String): ResponseEntity<String> =
        jsonRequest(HttpMethod.GET, url, String::class.java)

    override fun <T> get(url: String, responseType: Class<T>): ResponseEntity<T> =
        jsonRequest(HttpMethod.GET, url, responseType)

    override fun <T> get(
        url: String,
        responseType: ParameterizedTypeReference<T>
    ): ResponseEntity<T> = restTemplate.exchange(url, HttpMethod.GET, null, responseType)

    override fun put(url: String, body: String): ResponseEntity<String> =
        jsonRequest(HttpMethod.PUT, url, String::class.java, body)

    override fun put(url: String, body: Any): ResponseEntity<String> =
        jsonRequest(HttpMethod.PUT, url, String::class.java, body)

    override fun <T, R> put(url: String, body: R, responseType: Class<T>): ResponseEntity<T> =
        jsonRequest(HttpMethod.PUT, url, responseType, body)

    override fun post(url: String, body: Any?): ResponseEntity<String> =
        post(url, body, String::class.java)

    override fun <T> post(url: String, body: Any?, responseType: Class<T>): ResponseEntity<T> {
        return jsonRequest(HttpMethod.POST, url, responseType, body)
    }

    override fun patch(url: String, body: String?) =
        jsonRequest(HttpMethod.PATCH, url, String::class.java, body)

    override fun delete(url: String, body: Any?): ResponseEntity<String> =
        jsonRequest(HttpMethod.DELETE, url, String::class.java, body)

    private fun <T> jsonRequest(
        method: HttpMethod,
        url: String,
        responseType: Class<T>,
        body: Any? = null,
        headers: HttpHeaders = defaultHeaders,
    ): ResponseEntity<T> =
        restTemplate.exchange(url, method, HttpEntity(body, headers), responseType)
}
