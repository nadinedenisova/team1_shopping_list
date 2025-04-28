package com.practicum.shoppinglist.main.data.impl.auth.dto

sealed interface AuthRequest {
    data object Login : AuthRequest
    data object Registration : AuthRequest
    data object RefreshToken : AuthRequest
    data object Validation : AuthRequest
}