package com.github.simonhauck.budgetturtle.server.dataimport.adapter.http

import arrow.core.getOrHandle
import com.github.simonhauck.budgetturtle.server.dataimport.domain.model.Transaction
import com.github.simonhauck.budgetturtle.server.dataimport.domain.service.DataImportService
import java.util.Base64
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/import")
class ImportDataController(
    private val dataImportService: DataImportService,
) {

    @PostMapping("")
    fun importBankStatementCsv(@RequestBody encodedFileDto: EncodedFileDto): ImportResultDto {
        val transactionDetails =
            dataImportService.importCsv(decodeBase64(encodedFileDto.base64Content))

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
