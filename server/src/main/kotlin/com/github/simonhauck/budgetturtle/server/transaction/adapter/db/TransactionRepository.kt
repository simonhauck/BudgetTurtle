package com.github.simonhauck.budgetturtle.server.transaction.adapter.db

import arrow.core.Either
import com.github.simonhauck.budgetturtle.server.transaction.domain.model.Transaction
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.getCollection
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
}
