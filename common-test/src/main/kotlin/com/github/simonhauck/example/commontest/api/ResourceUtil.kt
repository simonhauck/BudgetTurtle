package com.github.simonhauck.example.commontest.api

import org.json.JSONObject

interface ResourceUtil {

    fun init(testClazz: Class<Any>)
    fun readResource(resourceUrl: String): List<String>

    fun readAndNormalize(resourceName: String): String
    fun readAndNormalizeToJson(resourceName: String): JSONObject
    fun read(resourceName: String): String
    fun readFromClassLoader(resourceName: String): String
    fun readAsByteArray(resourceName: String): ByteArray

    fun <T> readAndConvertToObject(resourceName: String, targetClass: Class<T>): T
    fun <T> readAndConvertToList(resourceName: String, targetClass: Class<T>): List<T>

    fun logAsJsonObject(something: Any)
}
