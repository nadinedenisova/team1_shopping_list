package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.domain.api.MainScreenRepository

class CopyShoppingListUseCase(
    private val repository: MainScreenRepository
) {
    suspend operator fun invoke(list: ListItem) {
        return repository.copyShoppingList(list)
    }
}