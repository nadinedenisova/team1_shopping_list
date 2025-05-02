package com.practicum.shoppinglist.main.domain.api

interface TokenStorage {
    fun saveToken(token: String)
    fun saveRefreshToken(token: String)
    fun getToken(): String?
    fun getRefreshToken(): String?
    fun clearToken()
}
