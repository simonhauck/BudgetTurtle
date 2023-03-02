package com.github.simonhauck.budgetturtle.server.testutil.driver

import com.github.simonhauck.budgetturtle.server.transaction.adapter.db.TransactionRepository
import com.github.simonhauck.budgetturtle.server.transaction.domain.model.Transaction
import com.github.simonhauck.budgetturtle.server.transaction.domain.model.TransactionDetails
import java.time.LocalDate
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class TransactionUtilImpl(
    private val transactionRepository: TransactionRepository,
) : TransactionUtil {

    override fun createTransactionForUser(userId: String): List<Transaction> {
        val startDate = LocalDate.of(2021, 1, 24)
        val initialTransaction = 1000.25.toBigDecimal()
        val longNamesList = names + names + names

        val transactions =
            longNamesList
                .mapIndexed { index, map ->
                    val transactionAmount =
                        initialTransaction.plus(index.toBigDecimal() * 15.0.toBigDecimal())
                    val finalTransaction =
                        if (index.mod(2) == 0) transactionAmount.negate() else transactionAmount

                    TransactionDetails(
                        startDate.plusDays(5.toLong() * index).toString(),
                        map.first,
                        map.second,
                        map.third,
                        finalTransaction
                    )
                }
                .map { Transaction(userId, it) }

        transactionRepository.insertMany(transactions)
        return transactions
    }

    companion object {
        private val names =
            listOf(
                Triple("VISA Some Company GMBH", "Lastschrift", "Payed for the best food"),
                Triple("Other Comany", "Überweisung", "Some custom usage"),
                Triple("Some food company", "Lastschrift", "Payed for the best food"),
                Triple("Some other food company", "Lastschrift", "Google Pay"),
                Triple("Grandma", "Gutschrift", "Support grandchildren"),
                Triple("ING", "Entgelt", "Money for giroCard"),
                Triple("Roommate", "Gutschrift aus Dauerauftrag", "Anteil Miete"),
                Triple("Some random Person", "Dauerauftrag / Terminueberweisung", "Rent"),
                Triple("The uncreative company", "Lastschrift", "Some text"),
                Triple(
                    "Saving Company",
                    "Dauerauftrag / Terminueberweisung",
                    "Shut up and take my money"
                ),
                Triple("Best company", "Gehalt/Rente", "Lohn"),
            )
    }
}
