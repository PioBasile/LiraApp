package com.app.lira.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Recurring(
    val id: Int? = null,
    val user_id: String,
    val name: String,
    val amount: Double,
    val start_date: String,
    val interval: String, // "daily", "weekly", "monthly", "yearly"
    val occurrences: Int? = null,
    val created_at: String? = null
)
