package com.practicum.shoppinglist.common.resources

sealed interface BaseIntent : ShoppingListIntent, DetailsScreenIntent {
    class RemoveListItem(val id: Long) : BaseIntent
    data object QueryRemoveShoppingList : BaseIntent
}