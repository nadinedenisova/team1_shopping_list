package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.data.impl.MainScreenRepositoryImpl

class RemoveAllShoppingListsUseCase(
    private val repository: MainScreenRepositoryImpl
) {
    suspend operator fun invoke() {
        return repository.removeAllShoppingLists()
    }
}