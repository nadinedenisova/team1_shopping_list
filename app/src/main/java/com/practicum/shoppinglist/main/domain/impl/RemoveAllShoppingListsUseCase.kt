package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.domain.api.MainScreenRepository

class RemoveAllShoppingListsUseCase(
    private val repository: MainScreenRepository
) {
    suspend operator fun invoke() {
        return repository.removeAllShoppingLists()
    }
}