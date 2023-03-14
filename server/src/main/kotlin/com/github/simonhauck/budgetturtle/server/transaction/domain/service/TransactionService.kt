package com.github.simonhauck.budgetturtle.server.transaction.domain.service

import com.github.simonhauck.budgetturtle.server.transaction.adapter.db.TransactionRepository
import com.github.simonhauck.budgetturtle.server.transaction.domain.model.Transaction
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
) {

    fun getTransactionsAfter(
        userId: String,
        lastSeenID: String?,
        requestedAmount: Int
    ): List<Transaction> {
        return transactionRepository.getLatestTransactionForUser(
            userId,
            requestedAmount,
            lastSeenID
        )
    }
}
