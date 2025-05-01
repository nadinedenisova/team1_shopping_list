package com.practicum.shoppinglist.main.data.impl.auth

import android.content.SharedPreferences
import com.practicum.shoppinglist.main.domain.api.TokenStorage
import jakarta.inject.Inject
import androidx.core.content.edit

class TokenStorageImpl@Inject constructor(
    private val sharedPreferences: SharedPreferences
) : TokenStorage {
    override fun saveToken(token: String) {
        sharedPreferences.edit() { putString("auth_token", token) }
    }

    override fun saveRefreshToken(token: String) {
        sharedPreferences.edit() { putString("auth_refresh_token", token) }
    }

    override fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    override fun getRefreshToken(): String? {
        return sharedPreferences.getString("auth_refresh_token", null)
    }

    override fun clearToken() {
        sharedPreferences.edit() { remove("auth_token") }
        sharedPreferences.edit() { remove("auth_refresh_token") }
    }
}