package com.github.simonhauck.example.commontest.testutil

import java.util.function.Consumer
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.ObjectAssert
import org.mockito.Mockito
import org.mockito.kotlin.argThat

object AssertionMatcher {

    fun <T> recRefEq(expected: T): T = matchesAssert { arg: T ->
        assertThat(arg).usingRecursiveComparison().isEqualTo(expected)
    }

    private fun <T> matchesAssert(assertion: Consumer<T>): T =
        Mockito.argThat { arg: T -> runAndConvertException { assertion.accept(arg) } }

    private fun runAndConvertException(assertion: Runnable): Boolean =
        assertThatCode { assertion.run() }.doesNotThrowAnyException().let { true }

    inline fun <reified T> argAssertion(crossinline assertion: ObjectAssert<T>.() -> Unit): T =
        argThat {
            assertion(assertThat(this))
            true
        }
}
