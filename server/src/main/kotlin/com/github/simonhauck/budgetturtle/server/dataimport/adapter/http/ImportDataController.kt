package com.github.simonhauck.budgetturtle.server.dataimport.adapter.http

import com.github.simonhauck.budgetturtle.server.dataimport.domain.service.DataImportService
import com.github.simonhauck.budgetturtle.server.dataimport.domain.service.TransactionDetails
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

        return ImportResultDto(
            encodedFileDto.name,
            true,
            "",
            transactionDetails.map { TransactionDetailsDto.fromModel(it) }
        )
    }

    companion object {

        fun decodeBase64(content: String): String =
            Base64.getDecoder().decode(content).decodeToString()
    }
}

data class ImportResultDto(
    val name: String,
    val success: Boolean,
    val errorMsg: String,
    val detectedTransactions: List<TransactionDetailsDto>
)

data class TransactionDetailsDto(
    val date: String,
    val clientName: String,
    val bookingType: String,
    val purpose: String,
    val transactionAmount: Double
) {
    companion object {

        fun fromModel(model: TransactionDetails): TransactionDetailsDto {
            return TransactionDetailsDto(
                date = model.date,
                clientName = model.clientName,
                bookingType = model.bookingType,
                purpose = model.purpose,
                transactionAmount = model.transactionAmount.toDouble()
            )
        }
    }
}

data class EncodedFileDto(
    val name: String,
    val base64Content: String,
)
