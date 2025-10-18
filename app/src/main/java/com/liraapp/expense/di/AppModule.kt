package com.liraapp.expense.di

import com.liraapp.expense.data.remote.SupabaseService
import com.liraapp.expense.data.repository.AuthRepositoryImpl
import com.liraapp.expense.data.repository.ExpenseRepositoryImpl
import com.liraapp.expense.domain.repository.AuthRepository
import com.liraapp.expense.domain.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://your-project.supabase.co",
            supabaseKey = "your-anon-key"
        ) {
            install(Auth)
            install(Postgrest)
        }
    }

    @Provides
    @Singleton
    fun provideSupabaseService(client: SupabaseClient): SupabaseService {
        return SupabaseService(client)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(service: SupabaseService): AuthRepository {
        return AuthRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(service: SupabaseService): ExpenseRepository {
        return ExpenseRepositoryImpl(service)
    }
}
