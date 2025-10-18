package com.liraapp.expense.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseDto(
    @SerialName("id")
    val id: String = "",
    @SerialName("user_id")
    val userId: String,
    @SerialName("amount")
    val amount: Double,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("date")
    val date: Long,
    @SerialName("created_at")
    val createdAt: Long = System.currentTimeMillis()
)
