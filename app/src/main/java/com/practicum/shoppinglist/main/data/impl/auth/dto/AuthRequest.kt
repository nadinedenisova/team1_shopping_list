package com.practicum.shoppinglist.main.data.impl.auth.dto

sealed interface AuthRequest {
    data object Login : AuthRequest
}