package com.app.lira.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val id: Int? = null,
    val user_id: String,
    val category_id: Int?,
    val amount: Double,
    val type: String, // "payed" ou "received"
    val source: String? = null,
    val description: String? = null,
    val date: String? = null
)