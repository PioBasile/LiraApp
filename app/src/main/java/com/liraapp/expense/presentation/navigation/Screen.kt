package com.liraapp.expense.presentation.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Home : Screen("home")
    object AddExpense : Screen("add_expense")
}
