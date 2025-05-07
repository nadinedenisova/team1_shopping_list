package com.practicum.shoppinglist.common.resources

import com.practicum.shoppinglist.core.domain.models.ListItem

data class ShoppingListState(
    val loggedIn: Boolean = false,
    val darkTheme: Boolean = false,
    val nothingFound: Boolean = false,
    val searchQuery: String = "",
    val selectedList: ListItem = ListItem(),
    val results: List<ListItem> = emptyList(),
    val content: List<ListItem> = emptyList(),
) {

    companion object {
        fun ShoppingListState.loggedIn(loggedIn: Boolean) = this.copy(loggedIn = loggedIn)
        fun ShoppingListState.selectedList(selectedList: ListItem) = this.copy(selectedList = selectedList)
        fun ShoppingListState.darkTheme(darkTheme: Boolean) = this.copy(darkTheme = darkTheme)
        fun ShoppingListState.nothingFound() = this.copy(
            nothingFound = true,
            results = emptyList(),
        )
        fun ShoppingListState.content(content: List<ListItem>) = this.copy(content = content)
        fun ShoppingListState.searchResults(searchQuery: String, results: List<ListItem>) = this.copy(
            nothingFound = false,
            searchQuery = searchQuery,
            results = results,
        )
        fun ShoppingListState.clearSearchResults() = this.copy(
            nothingFound = false,
            searchQuery = "",
            results = emptyList(),
        )
    }
}