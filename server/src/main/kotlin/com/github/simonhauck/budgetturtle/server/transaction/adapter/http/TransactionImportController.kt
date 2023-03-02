package com.github.simonhauck.budgetturtle.server.transaction.adapter.http

import arrow.core.getOrHandle
import com.github.simonhauck.budgetturtle.server.transaction.domain.model.Transaction
import com.github.simonhauck.budgetturtle.server.transaction.domain.service.DataImportService
import java.util.Base64
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
        val content = decodeBase64(encodedFileDto.base64Content)
        val transactionDetails = dataImportService.importCsv(userId = userId, content = content)

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

        fun decodeBase64(content: String): String =
            Base64.getDecoder().decode(content).decodeToString()
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
