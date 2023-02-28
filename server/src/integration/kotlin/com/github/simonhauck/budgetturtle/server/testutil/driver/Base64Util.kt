package com.github.simonhauck.budgetturtle.server.testutil.driver

import java.util.Base64

object Base64Util {

    fun encode(content: ByteArray): String = Base64.getEncoder().encodeToString(content)
}
