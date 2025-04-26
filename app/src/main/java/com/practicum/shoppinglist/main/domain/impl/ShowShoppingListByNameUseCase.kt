package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.domain.api.MainScreenRepository
import kotlinx.coroutines.flow.Flow

class ShowShoppingListByNameUseCase(
    private val repository: MainScreenRepository
) {
    suspend operator fun invoke(name: String): Flow<List<ListItem>> {
        return repository.showShoppingListByName(name)
    }
}