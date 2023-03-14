package com.github.simonhauck.budgetturtle.server.transaction.adapter.http

import arrow.core.getOrHandle
import com.github.simonhauck.budgetturtle.server.transaction.domain.model.Transaction
import com.github.simonhauck.budgetturtle.server.transaction.domain.service.DataImportService
import java.nio.charset.Charset
import java.util.Base64
import org.mozilla.universalchardet.UniversalDetector
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transaction/import")
class TransactionImportController(
    private val dataImportService: DataImportService,
) {

    @PostMapping("/{userId}")
    fun importBankStatementCsv(
        @PathVariable userId: String,
        @RequestBody encodedFileDto: EncodedFileDto,
    ): ImportResultDto {
        val decodeContent =
            decodeBase64WithCharSetDetection(encodedFileDto.base64Content)
                ?: return getImportFailedResult(encodedFileDto, "Failed to decode file")

        val transactionDetails =
            dataImportService.importCsv(userId = userId, content = decodeContent)

        return transactionDetails
            .map { transactions -> getSuccessResult(encodedFileDto, transactions) }
            .getOrHandle { getImportFailedResult(encodedFileDto, it) }
    }

    private fun getImportFailedResult(encodedFileDto: EncodedFileDto, it: String): ImportResultDto =
        ImportResultDto(
            name = encodedFileDto.name,
            success = false,
            errorMsg = it,
            detectedTransactions = emptyList()
        )

    private fun getSuccessResult(
        encodedFileDto: EncodedFileDto,
        transactions: List<Transaction>
    ): ImportResultDto =
        ImportResultDto(
            name = encodedFileDto.name,
            success = true,
            errorMsg = "",
            detectedTransactions = transactions.map { TransactionDto.fromModel(it) }
        )

    companion object {

        fun decodeBase64WithCharSetDetection(content: String): String? {
            val decodedBytes = Base64.getDecoder().decode(content)

            val charSetDetector =
                UniversalDetector().apply {
                    handleData(decodedBytes)
                    dataEnd()
                }

            val detectedCharset = charSetDetector.detectedCharset ?: return null

            return String(decodedBytes, Charset.forName(detectedCharset))
        }
    }
}

data class ImportResultDto(
    val name: String,
    val success: Boolean,
    val errorMsg: String,
    val detectedTransactions: List<TransactionDto>
)

data class EncodedFileDto(
    val name: String,
    val base64Content: String,
)
