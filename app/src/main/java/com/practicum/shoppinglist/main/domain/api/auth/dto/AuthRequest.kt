package com.practicum.shoppinglist.main.domain.api.auth.dto

sealed interface AuthRequest {
    data object Login : AuthRequest
}