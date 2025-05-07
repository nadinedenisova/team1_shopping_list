package com.practicum.shoppinglist.main.data.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.practicum.shoppinglist.common.utils.Constants
import com.practicum.shoppinglist.main.domain.api.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
) : SettingsRepository {

    override fun getThemeSettings(): Boolean {
        return prefs.getBoolean(Constants.THEME_SAVE_KEY, false)
    }

    override fun changeThemeChange(darkTheme: Boolean) {
        prefs.edit {
            putBoolean(Constants.THEME_SAVE_KEY, darkTheme)
        }
    }
}