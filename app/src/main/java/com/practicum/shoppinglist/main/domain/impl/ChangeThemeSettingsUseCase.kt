package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.domain.api.SettingsRepository

class ChangeThemeSettingsUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(darkTheme: Boolean) {
        return repository.changeThemeChange(darkTheme)
    }
}