package com.practicum.shoppinglist.main.domain.api

interface SettingsRepository {
    fun getThemeSettings(): Boolean
    fun changeThemeChange(darkTheme: Boolean)
}