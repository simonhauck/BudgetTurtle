package com.github.simonhauck.budgetturtle.server.dataimport.adapter.http

import com.github.simonhauck.budgetturtle.server.IntegrationTest
import com.github.simonhauck.budgetturtle.server.testutil.driver.Base64Util
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ImportDataControllerIT : IntegrationTest() {

    @Test
    fun `imported data should be available afterwards via the get endpoint`() =
        testDriver(this) {
            val fileContent = Base64Util.encode(readAsByteArray("ing_export.csv"))

            val actual = post("/api/import", EncodedFileDto("someFile.csv", fileContent))

            actual.body.assertJsonEquals(
                readAndNormalize("importResult_expected.json"),
                "detectedTransactions[*].id"
            )
        }
}
