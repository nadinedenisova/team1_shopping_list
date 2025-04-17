package com.practicum.shoppinglist.common.resources

import com.practicum.shoppinglist.core.domain.models.ListItem

sealed class ShoppingListState {
    data object NoShoppingLists: ShoppingListState()
    class ShoppingList(
        var list: List<ListItem>,
    ) : ShoppingListState()
}