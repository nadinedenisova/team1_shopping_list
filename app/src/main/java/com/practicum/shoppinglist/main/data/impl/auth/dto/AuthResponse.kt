package com.android.shoppinglist.feature.http.data.network

import com.practicum.shoppinglist.core.domain.models.auth.Login
import com.practicum.shoppinglist.core.domain.models.auth.Refresh
import com.practicum.shoppinglist.core.domain.models.auth.Registration
import com.practicum.shoppinglist.core.domain.models.auth.Validation
import com.practicum.shoppinglist.core.domain.models.auth.Recovery
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface AuthResponse {
    @Serializable
    class Login(
        @SerialName("user_id") val user_id: Int,
        @SerialName("access_token") val access_token: String,
        @SerialName("refresh_token") val refresh_token: String
    ) : AuthResponse

    @Serializable
    class Registration(
        @SerialName("user_id") val user_id: Int,
        @SerialName("access_token") val access_token: String,
        @SerialName("refresh_token") val refresh_token: String
    ) : AuthResponse

    @Serializable
    class Validation(
        @SerialName("is_valid") val is_valid: Boolean,
        @SerialName("success") val success: Boolean,
    ) : AuthResponse

    @Serializable
    class Refresh(
        @SerialName("refresh_token") val refresh_token: String,
        @SerialName("access_token") val access_token: String
    ) : AuthResponse

    @Serializable
    class Recovery : AuthResponse

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
        refreshToken = refresh_token,
        token = access_token
    )
}

fun AuthResponse.Recovery.mapToDomain(): Recovery {
    return Recovery()
}