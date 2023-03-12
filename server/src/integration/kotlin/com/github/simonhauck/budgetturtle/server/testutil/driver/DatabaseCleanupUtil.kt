package com.github.simonhauck.budgetturtle.server.testutil.driver

import com.github.simonhauck.budgetturtle.server.transaction.domain.model.Transaction
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
import org.springframework.stereotype.Component

@Component
class DatabaseCleanupUtil(
    private val database: MongoDatabase,
) {

    fun removeAllDataForUser(userId: String) {
        database.getCollection<Transaction>().deleteMany(Transaction::userId eq userId)
    }
}
