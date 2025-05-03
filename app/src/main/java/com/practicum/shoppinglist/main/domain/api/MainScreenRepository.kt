package com.practicum.shoppinglist.main.domain.api

import com.practicum.shoppinglist.core.domain.models.ListItem
import kotlinx.coroutines.flow.Flow

interface MainScreenRepository {
    suspend fun showShoppingLists(): Flow<List<ListItem>>
    suspend fun showShoppingListByName(name: String): Flow<List<ListItem>>
    suspend fun addShoppingList(name: String, icon: Long)
    suspend fun removeShoppingList(id: Long)
    suspend fun removeAllShoppingLists()
    suspend fun updateShoppingLIst(list: ListItem)
    fun getThemeSettings(): Boolean
    fun changeThemeChange(darkTheme: Boolean)
}