package com.app.lira.data.repository


import com.app.lira.data.remote.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AuthRepository {

    private val client = SupabaseClient.client

    // --- Sign up function ---
    suspend fun signUp(email: String, password: String): Result<String> = withContext(Dispatchers.IO) {
        return@withContext try {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success("Account created successfully!")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Sign in function ---
    suspend fun signIn(email: String, password: String): Result<String> = withContext(Dispatchers.IO) {
        return@withContext try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success("Logged in successfully!")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Sign out function (optional) ---
    suspend fun signOut(): Result<String> = withContext(Dispatchers.IO) {
        return@withContext try {
            client.auth.signOut()
            Result.success("Signed out successfully!")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}