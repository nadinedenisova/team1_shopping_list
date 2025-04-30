package com.practicum.shoppinglist.common.resources

import com.practicum.shoppinglist.core.domain.models.ListItem

data class ShoppingListState(
    val status: Status,
    val darkTheme: Boolean = false,
    val searchQuery: String? = null,
    val results: List<ListItem>? = null,
    val content: List<ListItem>? = null,
) {

    enum class Status {
        DEFAULT,
        NOTHING_FOUND,
        NO_SHOPPING_LISTS,
        SEARCH_RESULTS,
        CONTENT,
    }

    companion object {
        fun default() = ShoppingListState(Status.DEFAULT)
        fun ShoppingListState.darkTheme(darkTheme: Boolean) = this.copy(darkTheme = darkTheme)
        fun ShoppingListState.nothingFound(searchQuery: String) = this.copy(status = Status.NOTHING_FOUND, searchQuery = searchQuery)
        fun ShoppingListState.noShoppingLists() = this.copy(status = Status.NO_SHOPPING_LISTS)
        fun ShoppingListState.searchResults(searchQuery: String, results: List<ListItem>) = this.copy(status = Status.SEARCH_RESULTS, searchQuery = searchQuery, results = results)
        fun ShoppingListState.content(content: List<ListItem>) = this.copy(status = Status.CONTENT, content = content)
        fun ShoppingListState.content() = this.copy(status = Status.CONTENT, content = this.content)
    }
}