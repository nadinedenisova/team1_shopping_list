package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.domain.api.MainScreenRepository

class GetThemeSettingsUseCase(
    private val repository: MainScreenRepository
) {
    operator fun invoke(): Boolean {
        return repository.getThemeSettings()
    }
}