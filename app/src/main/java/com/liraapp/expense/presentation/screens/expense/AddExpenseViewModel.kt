package com.liraapp.expense.presentation.screens.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liraapp.expense.domain.model.Expense
import com.liraapp.expense.domain.model.ExpenseCategory
import com.liraapp.expense.domain.usecase.AddExpenseUseCase
import com.liraapp.expense.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddExpenseUiState>(AddExpenseUiState.Initial)
    val uiState: StateFlow<AddExpenseUiState> = _uiState.asStateFlow()

    fun addExpense(
        amount: Double,
        category: ExpenseCategory,
        description: String,
        date: Long
    ) {
        viewModelScope.launch {
            _uiState.value = AddExpenseUiState.Loading
            
            val user = getCurrentUserUseCase().firstOrNull()
            if (user == null) {
                _uiState.value = AddExpenseUiState.Error("User not found")
                return@launch
            }

            val expense = Expense(
                userId = user.id,
                amount = amount,
                category = category,
                description = description,
                date = date
            )

            val result = addExpenseUseCase(expense)
            _uiState.value = if (result.isSuccess) {
                AddExpenseUiState.Success
            } else {
                AddExpenseUiState.Error(result.exceptionOrNull()?.message ?: "Failed to add expense")
            }
        }
    }

    fun resetState() {
        _uiState.value = AddExpenseUiState.Initial
    }
}

sealed class AddExpenseUiState {
    object Initial : AddExpenseUiState()
    object Loading : AddExpenseUiState()
    object Success : AddExpenseUiState()
    data class Error(val message: String) : AddExpenseUiState()
}
