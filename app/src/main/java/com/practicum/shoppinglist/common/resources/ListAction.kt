package com.practicum.shoppinglist.common.resources

sealed interface ListAction {
    data object RemoveItem : ListAction
}