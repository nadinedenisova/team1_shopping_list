package com.practicum.shoppinglist.main.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.shoppinglist.common.resources.ShoppingListIntent
import com.practicum.shoppinglist.common.resources.ShoppingListState
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.content
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.darkTheme
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.default
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.isRemoving
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.noShoppingLists
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.nothingFound
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.searchResults
import com.practicum.shoppinglist.common.utils.Constants
import com.practicum.shoppinglist.common.utils.Debounce
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.domain.impl.AddShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.ChangeThemeSettingsUseCase
import com.practicum.shoppinglist.main.domain.impl.GetThemeSettingsUseCase
import com.practicum.shoppinglist.main.domain.impl.RemoveAllShoppingListsUseCase
import com.practicum.shoppinglist.main.domain.impl.RemoveShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListByNameUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListsUseCase
import com.practicum.shoppinglist.main.domain.impl.UpdateShoppingListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val showShoppingListsUseCase: ShowShoppingListsUseCase,
    private val showShoppingListByNameUseCase: ShowShoppingListByNameUseCase,
    private val addShoppingListsUseCase: AddShoppingListUseCase,
    private val updateShoppingListsUseCase: UpdateShoppingListUseCase,
    private val removeShoppingListUseCase: RemoveShoppingListUseCase,
    private val removeAllShoppingListsUseCase: RemoveAllShoppingListsUseCase,
    private val getThemeSettingsUseCase: GetThemeSettingsUseCase,
    private val changeThemeSettingsUseCase: ChangeThemeSettingsUseCase,
) : ViewModel() {

    private companion object {
        const val TAG = "MainScreenViewModel"
    }

    private val _shoppingListStateFlow = MutableStateFlow(default())
    val shoppingListStateFlow: StateFlow<ShoppingListState> = _shoppingListStateFlow.asStateFlow()

    private val timer: Debounce<String> by lazy {
        Debounce(Constants.USER_INPUT_DELAY, viewModelScope) { term ->
            doSearch(term)
        }
    }

    init {
        observeShoppingLists()
        processIntent(ShoppingListIntent.GetThemeSettings)
    }

    fun processIntent(intent: ShoppingListIntent) {
        when (intent) {
            is ShoppingListIntent.AddShoppingList -> addShoppingList(name = intent.name, icon = intent.icon)
            is ShoppingListIntent.UpdateShoppingList -> updateShoppingList(list = intent.list)
            is ShoppingListIntent.RemoveShoppingList -> removeShoppingList(id = intent.id)
            is ShoppingListIntent.RemoveAllShoppingLists -> removeAllShoppingLists()
            is ShoppingListIntent.Search -> search(searchQuery = intent.searchQuery)
            is ShoppingListIntent.ChangeThemeSettings -> changeThemeSettings(intent.darkTheme)
            is ShoppingListIntent.IsRemoving -> _shoppingListStateFlow.update { currentState ->
                currentState.isRemoving(intent.isRemoving)
            }
            is ShoppingListIntent.GetThemeSettings -> getThemeSettings()
            is ShoppingListIntent.ClearSearchResults -> clearSearchResults()
        }
    }

    private fun getThemeSettings() {
        _shoppingListStateFlow.update { currentState ->
            currentState.darkTheme(darkTheme = getThemeSettingsUseCase())
        }
    }

    private fun changeThemeSettings(darkTheme: Boolean) {
        changeThemeSettingsUseCase(darkTheme)
        _shoppingListStateFlow.update { currentState ->
            currentState.darkTheme(darkTheme = darkTheme)
        }
    }

    private fun addShoppingList(name: String, icon: Long) {
        viewModelScope.launch {
            runCatching {
                addShoppingListsUseCase(name, icon)
            }.onFailure { error ->
                Log.e(TAG, "error in add shopping list -> $error")
            }
        }
    }

    private fun updateShoppingList(list: ListItem) {
        viewModelScope.launch {
            runCatching {
                updateShoppingListsUseCase(list)
            }.onFailure { error ->
                Log.e(TAG, "error in update shopping list -> $error")
            }
        }
    }

    private fun removeShoppingList(id: Long) {
        viewModelScope.launch {
            runCatching {
                removeShoppingListUseCase(id)
            }.onFailure { error ->
                Log.e(TAG, "error in remove shopping list -> $error")
            }
        }
    }

    private fun removeAllShoppingLists() {
        viewModelScope.launch {
            runCatching {
                removeAllShoppingListsUseCase()
            }.onFailure { error ->
                Log.e(TAG, "error in remove all shopping lists -> $error")
            }
        }
    }

    private fun search(searchQuery: String) {
        timer.start(parameter = searchQuery)
    }

    private fun clearSearchResults() {
        _shoppingListStateFlow.update { currentState ->
            currentState.content()
        }
    }

    private fun doSearch(searchQuery: String?) {
        if (searchQuery.isNullOrEmpty()) return

        viewModelScope.launch {
            runCatching {
                showShoppingListByNameUseCase(searchQuery)
                    .collect { results ->
                        processSearchResult(searchQuery = searchQuery, results = results)
                    }
            }.onFailure { error ->
                Log.e(TAG, "error in search shopping list -> $error")
            }
        }
    }

    private fun processSearchResult(searchQuery: String, results: List<ListItem>) {
        _shoppingListStateFlow.update { currentState ->
            when {
                results.isNotEmpty() -> currentState.searchResults(searchQuery = searchQuery, results = results)
                else -> currentState.nothingFound(searchQuery)
            }
        }
    }

    private fun observeShoppingLists() {
        viewModelScope.launch {
            runCatching {
                showShoppingListsUseCase()
                    .collect {
                        processObserveShoppingListsResult(it)
                    }
            }.onFailure { error ->
                Log.e(TAG, "error in show shopping list -> $error")
            }
        }
    }

    private fun processObserveShoppingListsResult(list: List<ListItem>) {
           _shoppingListStateFlow.update { currentState ->
               when {
                   list.isNotEmpty() -> currentState.content(list)
                   else -> currentState.noShoppingLists()
            }
        }
    }
}