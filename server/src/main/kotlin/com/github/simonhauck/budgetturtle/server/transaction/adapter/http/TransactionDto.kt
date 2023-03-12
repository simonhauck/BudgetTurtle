package com.github.simonhauck.budgetturtle.server.transaction.adapter.http

import com.github.simonhauck.budgetturtle.server.transaction.domain.model.Transaction
import com.github.simonhauck.budgetturtle.server.transaction.domain.model.TransactionDetails

data class TransactionDto(val id: String, val userId: String, val details: TransactionDetailsDto) {
    companion object {

        fun fromModel(model: Transaction): TransactionDto =
            TransactionDto(
                id = model.id.toString(),
                userId = model.userId,
                details = TransactionDetailsDto.fromModel(model.details)
            )
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
                date = model.date.toString(),
                clientName = model.clientName,
                bookingType = model.bookingType,
                purpose = model.purpose,
                transactionAmount = model.transactionAmount.toDouble()
            )
        }
    }
}
