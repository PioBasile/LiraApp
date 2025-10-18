package com.liraapp.expense.data.repository

import com.liraapp.expense.data.remote.SupabaseService
import com.liraapp.expense.data.remote.toDomain
import com.liraapp.expense.data.remote.toDto
import com.liraapp.expense.domain.model.Expense
import com.liraapp.expense.domain.model.ExpenseCategory
import com.liraapp.expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepositoryImpl @Inject constructor(
    private val supabaseService: SupabaseService
) : ExpenseRepository {

    override suspend fun addExpense(expense: Expense): Result<Expense> {
        return try {
            val dto = supabaseService.insertExpense(expense.toDto())
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateExpense(expense: Expense): Result<Expense> {
        return try {
            val dto = supabaseService.updateExpense(expense.toDto())
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteExpense(expenseId: String): Result<Unit> {
        return try {
            supabaseService.deleteExpense(expenseId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getExpensesByMonth(userId: String, month: Int, year: Int): Flow<List<Expense>> = flow {
        try {
            val expenses = supabaseService.getExpensesByMonth(userId, month, year)
            emit(expenses.map { it.toDomain() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun getAllExpenses(userId: String): Flow<List<Expense>> = flow {
        try {
            val expenses = supabaseService.getAllExpenses(userId)
            emit(expenses.map { it.toDomain() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun getExpensesByCategory(
        userId: String,
        category: ExpenseCategory,
        month: Int,
        year: Int
    ): List<Expense> {
        return try {
            val expenses = supabaseService.getExpensesByMonth(userId, month, year)
            expenses.map { it.toDomain() }.filter { it.category == category }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
