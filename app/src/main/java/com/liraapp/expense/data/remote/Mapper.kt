package com.liraapp.expense.data.remote

import com.liraapp.expense.domain.model.Expense
import com.liraapp.expense.domain.model.ExpenseCategory

fun ExpenseDto.toDomain(): Expense {
    return Expense(
        id = id,
        userId = userId,
        amount = amount,
        category = ExpenseCategory.valueOf(category),
        description = description,
        date = date,
        createdAt = createdAt
    )
}

fun Expense.toDto(): ExpenseDto {
    return ExpenseDto(
        id = id,
        userId = userId,
        amount = amount,
        category = category.name,
        description = description,
        date = date,
        createdAt = createdAt
    )
}
