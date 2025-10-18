package com.liraapp.expense.domain.model

enum class ExpenseCategory {
    FOOD,
    TRANSPORT,
    ENTERTAINMENT,
    SHOPPING,
    BILLS,
    HEALTH,
    EDUCATION,
    OTHER;

    fun displayName(): String {
        return when (this) {
            FOOD -> "Food & Dining"
            TRANSPORT -> "Transport"
            ENTERTAINMENT -> "Entertainment"
            SHOPPING -> "Shopping"
            BILLS -> "Bills & Utilities"
            HEALTH -> "Health"
            EDUCATION -> "Education"
            OTHER -> "Other"
        }
    }
}
