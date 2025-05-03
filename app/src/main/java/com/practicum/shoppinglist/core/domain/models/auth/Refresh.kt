package com.practicum.shoppinglist.core.domain.models.auth

data class Refresh(
    val refreshToken: String,
    val token: String
)