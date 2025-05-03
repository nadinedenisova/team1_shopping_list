package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.domain.api.SettingsRepository

class GetThemeSettingsUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Boolean {
        return repository.getThemeSettings()
    }
}