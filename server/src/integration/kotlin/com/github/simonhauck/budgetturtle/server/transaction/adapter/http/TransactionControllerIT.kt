package com.github.simonhauck.budgetturtle.server.transaction.adapter.http

import com.github.simonhauck.budgetturtle.server.IntegrationTest
import org.junit.jupiter.api.Test

class TransactionControllerIT : IntegrationTest() {
    private val someExistingUser = "SomeFakeUser"

    @Test
    fun `get all returns an empty list of transactions for a new user`() =
        testDriver(this) {
            val actual = get("/api/transactions/user/someUnknownUser/last")

            actual.body.assertJsonEquals(readAndNormalize("getTransaction_new_user_expected.json"))
        }

    @Test
    fun `get all returns an the list of stored transactions for the existing user`() =
        testDriver(this) {
            createTransactionForUser(someExistingUser)

            val actual = get("/api/transactions/user/$someExistingUser/last")

            actual.body.assertJsonEquals(
                readAndNormalize("getTransaction_details_expected.json"),
                "transactions[*].id"
            )
        }
}
