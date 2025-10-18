package com.liraapp.expense.domain.repository

import com.liraapp.expense.domain.model.Expense
import com.liraapp.expense.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun addExpense(expense: Expense): Result<Expense>
    suspend fun updateExpense(expense: Expense): Result<Expense>
    suspend fun deleteExpense(expenseId: String): Result<Unit>
    fun getExpensesByMonth(userId: String, month: Int, year: Int): Flow<List<Expense>>
    fun getAllExpenses(userId: String): Flow<List<Expense>>
    suspend fun getExpensesByCategory(userId: String, category: ExpenseCategory, month: Int, year: Int): List<Expense>
}
