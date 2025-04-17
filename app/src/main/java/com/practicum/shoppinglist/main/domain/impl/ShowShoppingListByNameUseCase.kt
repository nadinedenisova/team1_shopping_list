package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.data.impl.MainScreenRepositoryImpl

class ShowShoppingListByNameUseCase(
    private val repository: MainScreenRepositoryImpl
) {
    suspend operator fun invoke(name: String) {
        return repository.showShoppingListByName(name)
    }
}