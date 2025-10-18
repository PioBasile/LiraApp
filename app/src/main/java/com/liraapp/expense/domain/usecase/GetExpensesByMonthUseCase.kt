package com.liraapp.expense.domain.usecase

import com.liraapp.expense.domain.model.Expense
import com.liraapp.expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesByMonthUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(userId: String, month: Int, year: Int): Flow<List<Expense>> {
        return repository.getExpensesByMonth(userId, month, year)
    }
}
