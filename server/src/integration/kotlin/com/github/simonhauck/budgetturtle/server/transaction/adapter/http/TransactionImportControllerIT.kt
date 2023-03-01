package com.github.simonhauck.budgetturtle.server.transaction.adapter.http

import com.github.simonhauck.budgetturtle.server.IntegrationTest
import com.github.simonhauck.budgetturtle.server.testutil.driver.Base64Util
import org.junit.jupiter.api.Test

class TransactionImportControllerIT : IntegrationTest() {

    @Test
    fun `the encoded csv file should be correctly parsed and saved in the database`() =
        testDriver(this) {
            val fileContent = Base64Util.encode(readAsByteArray("ing_export.csv"))

            val actual =
                post(
                    "/api/transaction/import/someUser",
                    EncodedFileDto("someFile.csv", fileContent)
                )

            actual.body.assertJsonEquals(
                readAndNormalize("importResult_expected.json"),
                "detectedTransactions[*].id"
            )
        }
}
