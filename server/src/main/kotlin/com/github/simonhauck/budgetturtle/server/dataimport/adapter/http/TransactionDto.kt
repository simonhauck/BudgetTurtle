package com.github.simonhauck.budgetturtle.server.dataimport.adapter.http

import com.github.simonhauck.budgetturtle.server.dataimport.domain.model.Transaction
import com.github.simonhauck.budgetturtle.server.dataimport.domain.model.TransactionDetails

data class TransactionDto(val id: String, val details: TransactionDetailsDto) {
    companion object {

        fun fromModel(model: Transaction): TransactionDto =
            TransactionDto(model.id.toString(), TransactionDetailsDto.fromModel(model.details))
    }
}

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
