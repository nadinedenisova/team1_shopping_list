package com.practicum.shoppinglist.main.data.impl.auth.dto

import kotlinx.serialization.Serializable

sealed interface AuthRequest {
    @Serializable
    data class Login(val email: String, val password: String) : AuthRequest
    @Serializable
    data class Registration(val email: String, val password: String) : AuthRequest
    @Serializable
    data class RefreshToken(val refresh_token: String, val token: String) : AuthRequest
    @Serializable
    data class Validation(val token: String) : AuthRequest
}