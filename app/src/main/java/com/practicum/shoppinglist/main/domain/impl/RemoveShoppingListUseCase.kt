package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.domain.api.MainScreenRepository

class RemoveShoppingListUseCase(
    private val repository: MainScreenRepository
) {
    suspend operator fun invoke(id: Long) {
        return repository.removeShoppingList(id)
    }
}