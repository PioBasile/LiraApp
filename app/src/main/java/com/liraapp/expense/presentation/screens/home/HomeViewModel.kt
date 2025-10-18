package com.liraapp.expense.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liraapp.expense.domain.model.MonthlySummary
import com.liraapp.expense.domain.usecase.GetCurrentUserUseCase
import com.liraapp.expense.domain.usecase.GetMonthlySummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMonthlySummaryUseCase: GetMonthlySummaryUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _selectedMonth = MutableStateFlow(Calendar.getInstance().get(Calendar.MONTH) + 1)
    val selectedMonth: StateFlow<Int> = _selectedMonth.asStateFlow()

    private val _selectedYear = MutableStateFlow(Calendar.getInstance().get(Calendar.YEAR))
    val selectedYear: StateFlow<Int> = _selectedYear.asStateFlow()

    init {
        loadMonthlySummary()
    }

    fun loadMonthlySummary() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                if (user != null) {
                    _uiState.value = HomeUiState.Loading
                    getMonthlySummaryUseCase(user.id, _selectedMonth.value, _selectedYear.value)
                        .collect { summary ->
                            _uiState.value = HomeUiState.Success(summary)
                        }
                } else {
                    _uiState.value = HomeUiState.Error("User not found")
                }
            }
        }
    }

    fun updateMonth(month: Int, year: Int) {
        _selectedMonth.value = month
        _selectedYear.value = year
        loadMonthlySummary()
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val summary: MonthlySummary) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
