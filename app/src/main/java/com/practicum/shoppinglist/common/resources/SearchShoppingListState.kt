package com.practicum.shoppinglist.common.resources

import com.practicum.shoppinglist.core.domain.models.ListItem

sealed class SearchShoppingListState(
    val term: String?,
) {
    data object Default: SearchShoppingListState(null)
    class NothingFound(
        term: String,
    ): SearchShoppingListState(term)
    class SearchResults(
        var list: List<ListItem>,
        term: String?,
    ) : SearchShoppingListState(term)
}