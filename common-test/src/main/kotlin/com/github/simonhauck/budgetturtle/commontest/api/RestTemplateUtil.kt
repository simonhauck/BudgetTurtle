package com.github.simonhauck.budgetturtle.commontest.api

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.ResponseEntity

interface RestTemplateUtil {

    fun get(url: String): ResponseEntity<String>
    fun <T> get(url: String, responseType: Class<T>): ResponseEntity<T>
    fun <T> get(url: String, responseType: ParameterizedTypeReference<T>): ResponseEntity<T>

    fun put(url: String, body: String): ResponseEntity<String>
    fun put(url: String, body: Any): ResponseEntity<String>
    fun <T, R> put(url: String, body: R, responseType: Class<T>): ResponseEntity<T>

    fun post(url: String, body: Any? = null): ResponseEntity<String>
    fun <T> post(url: String, body: Any? = null, responseType: Class<T>): ResponseEntity<T>

    fun patch(url: String, body: String?): ResponseEntity<String>

    fun delete(url: String, body: Any? = null): ResponseEntity<String>
}
