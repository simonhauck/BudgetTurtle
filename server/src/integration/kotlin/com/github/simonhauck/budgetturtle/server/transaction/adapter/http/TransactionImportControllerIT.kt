package com.github.simonhauck.budgetturtle.server.transaction.adapter.http

import com.github.simonhauck.budgetturtle.server.IntegrationTest
import com.github.simonhauck.budgetturtle.server.testutil.driver.Base64Util
import org.junit.jupiter.api.Test

class TransactionImportControllerIT : IntegrationTest() {

    @Test
    fun `imported data should be available afterwards via the get endpoint`() =
        testDriver(this) {
            val fileContent = Base64Util.encode(readAsByteArray("ing_export.csv"))

            val actual = post("/api/import/someUser", EncodedFileDto("someFile.csv", fileContent))

            actual.body.assertJsonEquals(
                readAndNormalize("importResult_expected.json"),
                "detectedTransactions[*].id"
            )
        }
}
