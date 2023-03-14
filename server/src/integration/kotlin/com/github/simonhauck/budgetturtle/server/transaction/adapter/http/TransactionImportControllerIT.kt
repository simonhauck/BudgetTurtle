package com.github.simonhauck.budgetturtle.server.transaction.adapter.http

import com.github.simonhauck.budgetturtle.server.IntegrationTest
import com.github.simonhauck.budgetturtle.server.testutil.driver.Base64Util
import java.nio.charset.Charset
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

    @Test
    fun `the as windows-1242 encoded file is also parsed correctly`() =
        testDriver(this) {
            val content = readAsByteArray("ing_export.csv").decodeToString()
            val fileContent =
                Base64Util.encode(content.toByteArray(Charset.forName("WINDOWS-1252")))

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
