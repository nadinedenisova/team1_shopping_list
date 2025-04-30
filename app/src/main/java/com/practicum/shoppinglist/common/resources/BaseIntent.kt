package com.practicum.shoppinglist.common.resources

sealed interface BaseIntent {
    class RemoveListItem(val id: Long) : ShoppingListIntent
    data object QueryRemoveShoppingList : ShoppingListIntent
}