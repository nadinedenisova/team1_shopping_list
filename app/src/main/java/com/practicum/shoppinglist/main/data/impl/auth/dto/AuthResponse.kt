package com.android.shoppinglist.feature.http.data.network

sealed interface AuthResponse {
    data object Login : AuthResponse
}