package com.github.simonhauck.budgetturtle.server.dataimport.domain.model

import java.math.BigDecimal
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class Transaction(
    val details: TransactionDetails,
    @BsonId val id: Id<Transaction> = newId(),
)

data class TransactionDetails(
    val date: String,
    val clientName: String,
    val bookingType: String,
    val purpose: String,
    val transactionAmount: BigDecimal
)
