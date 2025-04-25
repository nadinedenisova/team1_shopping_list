package com.practicum.shoppinglist.common.resources

import com.practicum.shoppinglist.core.domain.models.ListItem

sealed interface ShoppingListIntent {
    class AddShoppingList(
        val name: String,
        val icon: Long
    ) : ShoppingListIntent
    class UpdateShoppingList(val list: ListItem) : ShoppingListIntent
    class RemoveShoppingList(val id: Long) : ShoppingListIntent
    class Search(val searchQuery: String) : ShoppingListIntent
    class ChangeThemeSettings(val darkTheme: Boolean) : ShoppingListIntent
    class IsRemoving(val isRemoving: Boolean) : ShoppingListIntent
    data object RemoveAllShoppingLists : ShoppingListIntent
    data object GetThemeSettings : ShoppingListIntent
    data object ClearSearchResults : ShoppingListIntent
}