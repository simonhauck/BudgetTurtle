package com.github.simonhauck.budgetturtle.server.dataimport.domain.service

import arrow.core.Either
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.simonhauck.budgetturtle.server.dataimport.adapter.db.TransactionRepository
import com.github.simonhauck.budgetturtle.server.dataimport.domain.model.Transaction
import com.github.simonhauck.budgetturtle.server.dataimport.domain.model.TransactionDetails
import org.springframework.stereotype.Service

@Service
class DataImportService(
    private val transactionRepository: TransactionRepository,
) {

    fun importCsv(content: String): Either<String, List<Transaction>> {
        val content = stripContentOfPrefixAndSuffix(content).joinToString(System.lineSeparator())

        val lines =
            csvReader {
                    delimiter = ING_GER_DELIMITER
                    autoRenameDuplicateHeaders = true
                }
                .readAllWithHeader(content)

        val transactions =
            lines.map { it.extractTransactionDetails() }.map { Transaction(details = it) }

        return transactionRepository.insertMany(transactions)
    }

    private fun stripContentOfPrefixAndSuffix(content: String): List<String> {
        val contentWithOutEndingLines = content.lines().dropLastWhile { it.isBlank() }

        val indexLastEmptyLine = contentWithOutEndingLines.indexOfLast { it.isBlank() }
        if (indexLastEmptyLine < 0) return contentWithOutEndingLines

        return contentWithOutEndingLines.drop(indexLastEmptyLine + 1)
    }

    private fun Map<String, String>.extractTransactionDetails(): TransactionDetails {
        // TODO Simon.Hauck 2023-03-01 - handle money without cents properly
        val amount =
            getOrDefault(ING_GER_TRANSACTION_AMOUNT_KEY, "0.00")
                .replace(",", "")
                .replace(".", "")
                .toBigDecimal()
                .divide(100.0.toBigDecimal())

        return TransactionDetails(
            getOrDefault(ING_GER_DATE_KEY, ""),
            getOrDefault(ING_GER_CLIENT_NAME_KEY, ""),
            getOrDefault(ING_GER_CLIENT_BOOKING_TYPE_KEY, ""),
            getOrDefault(ING_GER_PURPOSE_KEY, ""),
            amount
        )
    }

    companion object {
        const val ING_GER_DELIMITER = ';'
        const val ING_GER_DATE_KEY = "Buchung"
        const val ING_GER_CLIENT_NAME_KEY = "Auftraggeber/Empfänger"
        const val ING_GER_CLIENT_BOOKING_TYPE_KEY = "Buchungstext"
        const val ING_GER_PURPOSE_KEY = "Verwendungszweck"
        const val ING_GER_TRANSACTION_AMOUNT_KEY = "Betrag"
    }
}
