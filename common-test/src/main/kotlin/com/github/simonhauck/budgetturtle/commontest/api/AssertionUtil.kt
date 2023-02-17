package com.github.simonhauck.budgetturtle.commontest.api

interface AssertionUtil {

    infix fun String?.assertJsonEquals(expected: String?)
    fun String?.assertJsonEquals(expected: String?, vararg ignoredFields: String)

    infix fun String?.assertJsonNotStrictlyEquals(expected: String)
    fun String?.isXmlEquals(expected: String?)
    fun awaitAssertion(assertion: () -> Unit)
}
