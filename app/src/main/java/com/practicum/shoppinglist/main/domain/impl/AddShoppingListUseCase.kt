package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.domain.api.MainScreenRepository

class AddShoppingListUseCase(
    private val repository: MainScreenRepository
) {
    suspend operator fun invoke(name: String, icon: Long) {
        return repository.addShoppingList(name, icon)
    }
}