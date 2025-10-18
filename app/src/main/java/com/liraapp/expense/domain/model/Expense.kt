package com.liraapp.expense.domain.model

data class Expense(
    val id: String = "",
    val userId: String,
    val amount: Double,
    val category: ExpenseCategory,
    val description: String,
    val date: Long, // timestamp in milliseconds
    val createdAt: Long = System.currentTimeMillis()
)
