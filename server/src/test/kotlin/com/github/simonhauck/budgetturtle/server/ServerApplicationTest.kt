package com.github.simonhauck.budgetturtle.server

import kotlin.math.pow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ServerApplicationTest {

    @Test
    fun smokeTest() {
        val actual = 2.0.pow(2)

        assertThat(actual).isEqualTo(4.0)
    }
}
