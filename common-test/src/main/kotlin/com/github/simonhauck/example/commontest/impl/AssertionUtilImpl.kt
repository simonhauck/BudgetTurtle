package com.github.simonhauck.example.commontest.impl

import com.github.simonhauck.example.commontest.api.AssertionUtil
import java.time.Duration
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility
import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.comparator.CustomComparator
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.xmlunit.assertj.XmlAssert

@Component
@Primary
class AssertionUtilImpl : AssertionUtil {

    override infix fun String?.assertJsonEquals(expected: String?) {
        assertJson(this, expected)
    }

    override fun String?.assertJsonEquals(expected: String?, vararg ignoredFields: String) {
        assertJson(this, expected, JSONCompareMode.STRICT, *ignoredFields)
    }

    override fun String?.isXmlEquals(expected: String?) {
        runCatching {
                XmlAssert.assertThat(this)
                    .and(expected)
                    .ignoreElementContentWhitespace()
                    .areIdentical()
            }
            .onFailure { assertThat(this).isEqualTo(expected) }
    }

    override fun String?.assertJsonNotStrictlyEquals(expected: String) {
        assertJson(this, expected, JSONCompareMode.LENIENT)
    }

    private fun assertJson(
        actual: String?,
        expected: String?,
        jsonCompareMode: JSONCompareMode = JSONCompareMode.STRICT,
        vararg ignoredFields: String,
    ) {
        val customizationList = ignoredFields.map { Customization(it) { _, _ -> true } }
        runCatching {
                JSONAssert.assertEquals(
                    expected,
                    actual,
                    CustomComparator(jsonCompareMode, *customizationList.toTypedArray()),
                )
            }
            .onFailure { assertThat(actual).isEqualTo(expected) }
    }

    override fun awaitAssertion(assertion: () -> Unit) {
        Awaitility.await().atMost(Duration.ofSeconds(5)).untilAsserted(assertion)
    }
}
