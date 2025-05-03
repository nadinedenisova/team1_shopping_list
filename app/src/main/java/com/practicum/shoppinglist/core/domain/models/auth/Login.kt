package com.practicum.shoppinglist.core.domain.models.auth

data class Login(
    val userId: Int,
    val accessToken: String,
    val refreshToken: String
)