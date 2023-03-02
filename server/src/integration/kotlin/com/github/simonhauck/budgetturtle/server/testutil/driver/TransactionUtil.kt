package com.github.simonhauck.budgetturtle.server.testutil.driver

import com.github.simonhauck.budgetturtle.server.transaction.domain.model.Transaction

interface TransactionUtil {

    fun createTransactionForUser(userId: String): List<Transaction>
}
