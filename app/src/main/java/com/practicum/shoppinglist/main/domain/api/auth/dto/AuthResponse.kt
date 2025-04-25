package com.android.ktorsample.feature.http.data.network

sealed interface AuthResponse {
    data object Login : AuthResponse
}