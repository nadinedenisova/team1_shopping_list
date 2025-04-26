package com.android.shoppinglist.feature.http.data.network

import kotlinx.serialization.Serializable

sealed interface AuthResponse {
    @Serializable
    class Login(
        val user_id: Int,
        val access_token: String,
        val refresh_token: String
    ) : AuthResponse

    @Serializable
    class Registration(
        val user_id: Int,
        val access_token: String,
        val refresh_token: String
    ) : AuthResponse

    class Validation(
        val is_valid: Boolean,
        val success: Boolean,
    ) : AuthResponse

    @Serializable
    class Refresh(
        val refresh_token: String
    ) : AuthResponse

}