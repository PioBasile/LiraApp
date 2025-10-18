package com.liraapp.expense.domain.model

data class MonthlySummary(
    val month: Int,
    val year: Int,
    val totalExpense: Double,
    val expensesByCategory: Map<ExpenseCategory, Double>,
    val expenses: List<Expense>
)
