package com.app.lira.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyLimit(
    val id: Int? = null,
    val user_id: String,
    val amount: Double,
    val period: String? = "daily",
    val created_at: String? = null
)