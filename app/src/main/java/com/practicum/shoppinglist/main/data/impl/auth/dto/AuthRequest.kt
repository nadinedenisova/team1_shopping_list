package com.practicum.shoppinglist.main.data.impl.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface AuthRequest {
    @Serializable
    data class Login(@SerialName("email") val email: String, @SerialName("password") val password: String) : AuthRequest
    @Serializable
    data class Registration(@SerialName("email") val email: String, @SerialName("password") val password: String) : AuthRequest
    @Serializable
    data class RefreshToken(@SerialName("refresh_token") val refresh_token: String, @SerialName("token") val token: String) : AuthRequest
    @Serializable
    data class Validation(@SerialName("token") val token: String) : AuthRequest
    @Serializable
    data class Recovery(@SerialName("email") val email: String) : AuthRequest
}