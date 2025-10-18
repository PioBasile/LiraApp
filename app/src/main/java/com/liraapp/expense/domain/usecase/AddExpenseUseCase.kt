package com.liraapp.expense.domain.usecase

import com.liraapp.expense.domain.model.Expense
import com.liraapp.expense.domain.repository.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense): Result<Expense> {
        return repository.addExpense(expense)
    }
}
