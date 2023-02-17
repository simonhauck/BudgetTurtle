package com.github.simonhauck.example.server

import com.github.simonhauck.example.commontest.config.EnableCommonTest
import com.github.simonhauck.example.server.testutil.driver.DefaultTestDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableCommonTest
class IntegrationTest {

    @Autowired lateinit var testDriver: DefaultTestDriver
}
