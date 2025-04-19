package com.practicum.shoppinglist.main.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.shoppinglist.common.resources.SearchShoppingListState
import com.practicum.shoppinglist.common.resources.ShoppingListState
import com.practicum.shoppinglist.common.utils.Constants
import com.practicum.shoppinglist.common.utils.Debounce
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.domain.api.MainScreenRepository
import com.practicum.shoppinglist.main.domain.impl.AddShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.RemoveShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListByNameUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListsUseCase
import com.practicum.shoppinglist.main.domain.impl.UpdateShoppingListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    mainScreenRepository: MainScreenRepository,
) : ViewModel() {

    private val _shoppingListStateFlow = MutableStateFlow<ShoppingListState>(ShoppingListState.Default)
    val shoppingListStateFlow: StateFlow<ShoppingListState> = _shoppingListStateFlow.asStateFlow()

    private val _searchShoppingListStateFlow = MutableStateFlow<SearchShoppingListState>(SearchShoppingListState.Default)
    val searchShoppingListStateFlow: StateFlow<SearchShoppingListState> = _searchShoppingListStateFlow.asStateFlow()

    private val showShoppingListsUseCase = ShowShoppingListsUseCase(mainScreenRepository)
    private val showShoppingListByNameUseCase = ShowShoppingListByNameUseCase(mainScreenRepository)
    private val addShoppingListsUseCase = AddShoppingListUseCase(mainScreenRepository)
    private val updateShoppingListsUseCase = UpdateShoppingListUseCase(mainScreenRepository)
    private val removeShoppingListUseCase = RemoveShoppingListUseCase(mainScreenRepository)

    private val timer: Debounce<String> by lazy {
        Debounce(Constants.USER_INPUT_DELAY, viewModelScope) { term ->
            doSearch(term)
        }
    }

    init {
        observeShoppingLists()
    }

    fun addShoppingList(name: String, icon: Long) {
        viewModelScope.launch {
            addShoppingListsUseCase(name, icon)
        }
    }

    fun updateShoppingList(list: ListItem) {
        viewModelScope.launch {
            updateShoppingListsUseCase(list)
        }
    }

    fun removeShoppingList(id: Long) {
        viewModelScope.launch {
            removeShoppingListUseCase(id)
        }
    }

    fun search(term: String) {
        timer.start(parameter = term)
    }

    fun clearSearchResults() {
        setSearchState(SearchShoppingListState.Default)
    }

    private fun doSearch(term: String?) {
        if (term.isNullOrEmpty()) return

        viewModelScope.launch {
            showShoppingListByNameUseCase(term)
                .collect { result ->
                    processSearchResult(result, term)
                }
        }
    }

    private fun processSearchResult(lists: List<ListItem>, term: String) {
        setSearchState(
            when {
                lists.isNotEmpty() -> {
                    SearchShoppingListState.SearchResults(lists, term)
                }
                else -> SearchShoppingListState.NothingFound(term)
            }
        )
    }

    private fun observeShoppingLists() {
        viewModelScope.launch {
            showShoppingListsUseCase()
                .collect {
                    processObserveShoppingListsResult(it)
                }
        }
    }

    private fun processObserveShoppingListsResult(list: List<ListItem>) {
        setState(
            when {
                list.isNotEmpty() -> ShoppingListState.ShoppingList(list)
                else -> ShoppingListState.NoShoppingLists
            }
        )
    }

    private fun setState(state: ShoppingListState) {
        _shoppingListStateFlow.value = state
    }

    private fun setSearchState(state: SearchShoppingListState) {
        _searchShoppingListStateFlow.value = state
    }

}