package com.github.simonhauck.budgetturtle.server.transaction.adapter.db

import arrow.core.Either
import com.github.simonhauck.budgetturtle.server.core.db.toObjectId
import com.github.simonhauck.budgetturtle.server.transaction.domain.model.Transaction
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.*
import org.springframework.stereotype.Component

@Component
class TransactionRepository(
    private val database: MongoDatabase,
) {

    fun insertMany(transactions: List<Transaction>): Either<String, List<Transaction>> {
        val result = database.getCollection<Transaction>().insertMany(transactions)

        return Either.conditionally(
            result.wasAcknowledged(),
            { "Some or all documents could not be imported" },
            { transactions }
        )
    }

    fun getLatestTransactionForUser(
        userId: String,
        requestedAmount: Int,
        lastSeenId: String?
    ): List<Transaction> {
        val lastSeenIdObj = lastSeenId?.toObjectId<Transaction>() ?: newId()

        return database
            .getCollection<Transaction>()
            .find(Transaction::userId eq userId, Transaction::id lt lastSeenIdObj)
            .descendingSort(Transaction::id)
            .limit(requestedAmount)
            .toList()
    }
}
