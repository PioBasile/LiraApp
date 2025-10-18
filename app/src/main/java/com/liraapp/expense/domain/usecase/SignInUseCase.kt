package com.liraapp.expense.domain.usecase

import com.liraapp.expense.domain.model.User
import com.liraapp.expense.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return repository.signIn(email, password)
    }
}
