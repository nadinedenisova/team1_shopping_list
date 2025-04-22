package com.practicum.shoppinglist.common.resources

import com.practicum.shoppinglist.core.domain.models.ListItem

sealed class ShoppingListIntent {
    data class AddShoppingList(
        val name: String,
        val icon: Long
    ) : ShoppingListIntent()
    data class UpdateShoppingList(val list: ListItem) : ShoppingListIntent()
    data class RemoveShoppingList(val id: Long) : ShoppingListIntent()
    data class Search(val searchQuery: String) : ShoppingListIntent()
    data object ClearSearchResults : ShoppingListIntent()
}