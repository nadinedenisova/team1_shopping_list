package com.practicum.shoppinglist.core.domain.models.auth

data class Registration(
    val userId: Int,
    val accessToken: String,
    val refreshToken: String
)