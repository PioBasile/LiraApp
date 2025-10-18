package com.liraapp.expense.data.repository

import com.liraapp.expense.data.remote.SupabaseService
import com.liraapp.expense.domain.model.User
import com.liraapp.expense.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val supabaseService: SupabaseService
) : AuthRepository {

    override suspend fun signUp(email: String, password: String): Result<User> {
        return try {
            supabaseService.signUp(email, password)
            val userId = supabaseService.getCurrentUserId()
            if (userId != null) {
                Result.success(User(id = userId, email = email))
            } else {
                Result.failure(Exception("Failed to get user ID after sign up"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            supabaseService.signIn(email, password)
            val userId = supabaseService.getCurrentUserId()
            val userEmail = supabaseService.getCurrentUserEmail()
            if (userId != null && userEmail != null) {
                Result.success(User(id = userId, email = userEmail))
            } else {
                Result.failure(Exception("Failed to get user info after sign in"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            supabaseService.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): Flow<User?> = flow {
        val userId = supabaseService.getCurrentUserId()
        val email = supabaseService.getCurrentUserEmail()
        if (userId != null && email != null) {
            emit(User(id = userId, email = email))
        } else {
            emit(null)
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return supabaseService.getCurrentUserId() != null
    }
}
