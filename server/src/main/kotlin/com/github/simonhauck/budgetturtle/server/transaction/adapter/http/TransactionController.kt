package com.github.simonhauck.budgetturtle.server.transaction.adapter.http

import com.github.simonhauck.budgetturtle.server.transaction.domain.service.TransactionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService,
) {

    @GetMapping("/user/{userId}/last")
    fun getTransactionPage(
        @PathVariable userId: String,
        @RequestParam lastSeenID: String?
    ): TransactionPageDto {
        val transactions = transactionService.getTransactionsAfter(userId, lastSeenID, 30)
        return TransactionPageDto(
            transactions = transactions.map { TransactionDto.fromModel(it) },
            canRequestMore = transactions.map { TransactionDto.fromModel(it) }.size == 30
        )
    }
}

data class TransactionPageDto(
    val transactions: List<TransactionDto>,
    val canRequestMore: Boolean,
)
