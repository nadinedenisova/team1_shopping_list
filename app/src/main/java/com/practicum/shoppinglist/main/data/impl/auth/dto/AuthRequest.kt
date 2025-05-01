package com.practicum.shoppinglist.main.data.impl.auth.dto

sealed interface AuthRequest {
    data class Login(val email: String, val password: String) : AuthRequest
    data class Registration(val email: String, val password: String) : AuthRequest
    data object RefreshToken : AuthRequest
    data object Validation : AuthRequest
}