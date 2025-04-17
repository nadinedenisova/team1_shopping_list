package com.practicum.shoppinglist.main.domain.api

import com.practicum.shoppinglist.core.domain.models.ListItem
import kotlinx.coroutines.flow.Flow

interface MainScreenRepository {
    suspend fun showShoppingLists(): Flow<List<ListItem>>
    suspend fun showShoppingListByName(name: String)
    suspend fun addShoppingList(list: ListItem)
    suspend fun removeAllShoppingLists()
    suspend fun updateShoppingLIst(list: ListItem)
}