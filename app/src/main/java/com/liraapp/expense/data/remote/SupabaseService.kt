package com.liraapp.expense.data.remote

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabaseService @Inject constructor(
    private val client: SupabaseClient
) {
    // Auth operations
    suspend fun signUp(email: String, password: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun signIn(email: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun signOut() {
        client.auth.signOut()
    }

    fun getCurrentUserId(): String? {
        return client.auth.currentUserOrNull()?.id
    }

    fun getCurrentUserEmail(): String? {
        return client.auth.currentUserOrNull()?.email
    }

    // Expense operations
    suspend fun insertExpense(expense: ExpenseDto): ExpenseDto {
        return client.from("expenses")
            .insert(expense) {
                select()
            }
            .decodeSingle<ExpenseDto>()
    }

    suspend fun updateExpense(expense: ExpenseDto): ExpenseDto {
        return client.from("expenses")
            .update(expense) {
                filter {
                    eq("id", expense.id)
                }
                select()
            }
            .decodeSingle<ExpenseDto>()
    }

    suspend fun deleteExpense(expenseId: String) {
        client.from("expenses")
            .delete {
                filter {
                    eq("id", expenseId)
                }
            }
    }

    suspend fun getExpensesByMonth(userId: String, month: Int, year: Int): List<ExpenseDto> {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1, 0, 0, 0)
        val startTime = calendar.timeInMillis

        calendar.set(year, month - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59)
        val endTime = calendar.timeInMillis

        return client.from("expenses")
            .select {
                filter {
                    eq("user_id", userId)
                    gte("date", startTime)
                    lte("date", endTime)
                }
            }
            .decodeList<ExpenseDto>()
    }

    suspend fun getAllExpenses(userId: String): List<ExpenseDto> {
        return client.from("expenses")
            .select {
                filter {
                    eq("user_id", userId)
                }
            }
            .decodeList<ExpenseDto>()
    }
}
