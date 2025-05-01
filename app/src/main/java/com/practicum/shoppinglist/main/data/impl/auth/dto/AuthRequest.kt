package com.practicum.shoppinglist.main.data.impl.auth.dto

import kotlinx.serialization.Serializable

sealed interface AuthRequest {
    @Serializable
    data class Login(val email: String, val password: String) : AuthRequest
    @Serializable
    data class Registration(val email: String, val password: String) : AuthRequest
    data object RefreshToken : AuthRequest
    data object Validation : AuthRequest
}
