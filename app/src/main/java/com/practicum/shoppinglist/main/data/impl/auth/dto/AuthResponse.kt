package com.android.shoppinglist.feature.http.data.network

import com.practicum.shoppinglist.core.domain.models.auth.Login
import com.practicum.shoppinglist.core.domain.models.auth.Refresh
import com.practicum.shoppinglist.core.domain.models.auth.Registration
import com.practicum.shoppinglist.core.domain.models.auth.Validation
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


fun AuthResponse.Login.mapToDomain(): Login {
    return Login(
        userId = user_id,
        accessToken = access_token,
        refreshToken = refresh_token
    )
}

fun AuthResponse.Registration.mapToDomain(): Registration {
    return Registration(
        userId = user_id,
        accessToken = access_token,
        refreshToken = refresh_token
    )
}

fun AuthResponse.Validation.mapToDomain(): Validation {
    return Validation(
        isValid = is_valid,
        success = success
    )
}

fun AuthResponse.Refresh.mapToDomain(): Refresh {
    return Refresh(
        refreshToken = refresh_token
    )
}
