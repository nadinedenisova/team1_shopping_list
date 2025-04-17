package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.data.impl.MainScreenRepositoryImpl
import kotlinx.coroutines.flow.Flow

class ShowShoppingListsUseCase(
    private val repository: MainScreenRepositoryImpl
) {
    suspend operator fun invoke(): Flow<List<ListItem>> {
        return repository.showShoppingLists()
    }
}