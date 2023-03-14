package com.github.simonhauck.budgetturtle.server.transaction.domain.service

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.springframework.stereotype.Component

@Component
class LocalDateParser {

    fun parseUnknownDateFormat(inputDate: String): LocalDate {
        val defaultLocalDate = LocalDate.now()

        return datePatterns
            .map { DateTimeFormatter.ofPattern(it) }
            .map { parseLocalDate(inputDate, it) }
            .firstOrNull()
            ?: defaultLocalDate
    }

    private fun parseLocalDate(inputDate: String, it: DateTimeFormatter): LocalDate? =
        runCatching { LocalDate.parse(inputDate, it) }.getOrNull()

    companion object {
        val datePatterns = listOf("dd.MM.yyyy")
    }
}
