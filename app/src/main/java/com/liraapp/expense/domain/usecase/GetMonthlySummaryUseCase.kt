package com.liraapp.expense.domain.usecase

import com.liraapp.expense.domain.model.ExpenseCategory
import com.liraapp.expense.domain.model.MonthlySummary
import com.liraapp.expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMonthlySummaryUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(userId: String, month: Int, year: Int): Flow<MonthlySummary> {
        return repository.getExpensesByMonth(userId, month, year).map { expenses ->
            val expensesByCategory = expenses.groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount } }
            
            val totalExpense = expenses.sumOf { it.amount }
            
            MonthlySummary(
                month = month,
                year = year,
                totalExpense = totalExpense,
                expensesByCategory = expensesByCategory,
                expenses = expenses
            )
        }
    }
}
