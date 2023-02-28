package com.github.simonhauck.budgetturtle.server.dataimport.adapter.http

import java.math.BigDecimal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/import")
class ImportDataController {

    @PostMapping("")
    fun importBankStatementCsv(@RequestBody encodedFileDto: EncodedFileDto): ImportResultDto {
        return ImportResultDto("some name", true, "", emptyList())
    }
}

data class ImportResultDto(
    val name: String,
    val success: Boolean,
    val errorMsg: String,
    val detectedTransactions: List<TransactionDto>
)

data class TransactionDto(
    val date: String,
    val clientName: String,
    val usageDescription: String,
    val transactionAmount: BigDecimal,
)

data class EncodedFileDto(
    val name: String,
    val base64Content: String,
)
