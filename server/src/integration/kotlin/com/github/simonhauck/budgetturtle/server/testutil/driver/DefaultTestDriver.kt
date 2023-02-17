package com.github.simonhauck.budgetturtle.server.testutil.driver

import com.github.simonhauck.budgetturtle.commontest.api.AssertionUtil
import com.github.simonhauck.budgetturtle.commontest.api.ResourceUtil
import com.github.simonhauck.budgetturtle.commontest.api.RestTemplateUtil
import java.util.*
import org.springframework.stereotype.Component

@Component
class DefaultTestDriver(
    private val restTemplateUtil: RestTemplateUtil,
    private val resourceUtil: ResourceUtil,
    private val assertionUtil: AssertionUtil,
) :
    RestTemplateUtil by restTemplateUtil,
    ResourceUtil by resourceUtil,
    AssertionUtil by assertionUtil {

    operator fun invoke(testClazz: Any, action: DefaultTestDriver.() -> Unit) {
        testDriverSetup(testClazz)
        action()
    }

    private fun testDriverSetup(testClazz: Any) {
        resourceUtil.init(testClazz.javaClass)
    }
}
