package com.github.simonhauck.budgetturtle.server

import com.github.simonhauck.budgetturtle.commontest.config.EnableCommonTest
import com.github.simonhauck.budgetturtle.server.testutil.driver.DefaultTestDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableCommonTest
class IntegrationTest {

    @Autowired lateinit var testDriver: DefaultTestDriver
}
