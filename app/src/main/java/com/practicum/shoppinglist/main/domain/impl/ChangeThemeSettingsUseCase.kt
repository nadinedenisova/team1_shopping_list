package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.domain.api.MainScreenRepository

class ChangeThemeSettingsUseCase(
    private val repository: MainScreenRepository
) {
    operator fun invoke(darkTheme: Boolean) {
        return repository.changeThemeChange(darkTheme)
    }
}