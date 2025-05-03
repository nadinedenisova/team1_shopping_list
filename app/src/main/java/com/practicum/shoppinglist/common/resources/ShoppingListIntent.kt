package com.practicum.shoppinglist.common.resources

import com.practicum.shoppinglist.core.domain.models.ListItem

sealed interface ShoppingListIntent {
    class AddShoppingList(
        val name: String,
        val icon: Long
    ) : ShoppingListIntent

    class UpdateShoppingList(val list: ListItem) : ShoppingListIntent
    class Search(val searchQuery: String) : ShoppingListIntent
    class ChangeThemeSettings(val darkTheme: Boolean) : ShoppingListIntent
    class SelectedList(val selectedList: ListItem) : ShoppingListIntent
    data object RemoveAllShoppingLists : ShoppingListIntent
    data object GetThemeSettings : ShoppingListIntent
    data object ClearSearchResults : ShoppingListIntent
}