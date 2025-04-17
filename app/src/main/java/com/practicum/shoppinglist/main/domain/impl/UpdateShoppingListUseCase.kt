package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.data.impl.MainScreenRepositoryImpl

class UpdateShoppingListUseCase(
    private val repository: MainScreenRepositoryImpl
) {
    suspend operator fun invoke(list: ListItem) {
        return repository.updateShoppingLIst(list)
    }
}