package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.domain.api.MainScreenRepository

class UpdateShoppingListUseCase(
    private val repository: MainScreenRepository
) {
    suspend operator fun invoke(list: ListItem) {
        return repository.updateShoppingLIst(list)
    }
}