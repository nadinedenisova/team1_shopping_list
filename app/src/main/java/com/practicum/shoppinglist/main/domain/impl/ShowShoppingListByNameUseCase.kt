package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.domain.api.MainScreenRepository

class ShowShoppingListByNameUseCase(
    private val repository: MainScreenRepository
) {
    suspend operator fun invoke(name: String) {
        return repository.showShoppingListByName(name)
    }
}