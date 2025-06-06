package com.practicum.shoppinglist.main.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.common.resources.ListAction
import com.practicum.shoppinglist.common.resources.ShoppingListIntent
import com.practicum.shoppinglist.common.resources.ShoppingListState
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.clearSearchResults
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.content
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.darkTheme
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.loggedIn
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.nothingFound
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.searchResults
import com.practicum.shoppinglist.common.resources.ShoppingListState.Companion.selectedList
import com.practicum.shoppinglist.common.utils.Constants
import com.practicum.shoppinglist.common.utils.Debounce
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.domain.impl.AddShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.ChangeThemeSettingsUseCase
import com.practicum.shoppinglist.main.domain.impl.CopyShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.GetThemeSettingsUseCase
import com.practicum.shoppinglist.main.domain.impl.IsUserLoggedInUseCase
import com.practicum.shoppinglist.main.domain.impl.LogoutUseCase
import com.practicum.shoppinglist.main.domain.impl.RemoveAllShoppingListsUseCase
import com.practicum.shoppinglist.main.domain.impl.RemoveShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListByNameUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListsUseCase
import com.practicum.shoppinglist.main.domain.impl.TokenValidationUseCase
import com.practicum.shoppinglist.main.domain.impl.UpdateShoppingListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val showShoppingListsUseCase: ShowShoppingListsUseCase,
    private val showShoppingListByNameUseCase: ShowShoppingListByNameUseCase,
    private val addShoppingListsUseCase: AddShoppingListUseCase,
    private val copyShoppingListsUseCase: CopyShoppingListUseCase,
    private val updateShoppingListsUseCase: UpdateShoppingListUseCase,
    private val removeShoppingListUseCase: RemoveShoppingListUseCase,
    private val removeAllShoppingListsUseCase: RemoveAllShoppingListsUseCase,
    private val getThemeSettingsUseCase: GetThemeSettingsUseCase,
    private val changeThemeSettingsUseCase: ChangeThemeSettingsUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val tokenValidationUseCase: TokenValidationUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private companion object {
        const val TAG = "MainScreenViewModel"
    }

    private val _shoppingListStateFlow = MutableStateFlow(ShoppingListState())
    val shoppingListStateFlow: StateFlow<ShoppingListState> = _shoppingListStateFlow.asStateFlow()

    private val _action = MutableSharedFlow<ListAction>()
    val action: SharedFlow<ListAction> = _action

    private val timer: Debounce<String> by lazy {
        Debounce(Constants.USER_INPUT_DELAY, viewModelScope) { term ->
            doSearch(term)
        }
    }

    init {
        checkLoginStatus()
        observeShoppingLists()
        processIntent(ShoppingListIntent.GetThemeSettings)
    }

    fun processIntent(intent: ShoppingListIntent) {
        when (intent) {
            is ShoppingListIntent.AddShoppingList -> addShoppingList(name = intent.name, icon = intent.icon)
            is ShoppingListIntent.CopyShoppingList -> copyShoppingList(list = intent.list)
            is ShoppingListIntent.UpdateShoppingList -> updateShoppingList(list = intent.list)
            is BaseIntent.RemoveListItem -> removeShoppingList(id = intent.id)
            is BaseIntent.QueryRemoveShoppingList -> viewModelScope.launch {
                _action.emit(ListAction.RemoveItem)
            }
            is ShoppingListIntent.RemoveAllShoppingLists -> removeAllShoppingLists()
            is ShoppingListIntent.Search -> search(searchQuery = intent.searchQuery)
            is ShoppingListIntent.SelectedList -> selectedList(intent.selectedList)
            is ShoppingListIntent.ChangeThemeSettings -> changeThemeSettings(intent.darkTheme)
            is ShoppingListIntent.GetThemeSettings -> getThemeSettings()
            is ShoppingListIntent.ClearSearchResults -> clearSearchResults()
            is ShoppingListIntent.Logout -> logout()
        }
    }

    private fun checkLoginStatus() {
        val loggedIn = isUserLoggedInUseCase()

        updateState(_shoppingListStateFlow.value.loggedIn(loggedIn = loggedIn))

        if (loggedIn) {
            validateToken()
        }
    }

    private fun validateToken() {
        viewModelScope.launch {
            val valid = tokenValidationUseCase()
            if (!valid) {
                logout()
            }
        }
    }

    private fun logout() {
        logoutUseCase()
        checkLoginStatus()
    }

    private fun getThemeSettings() {
        updateState(_shoppingListStateFlow.value.darkTheme(darkTheme = getThemeSettingsUseCase()))
    }

    private fun changeThemeSettings(darkTheme: Boolean) {
        changeThemeSettingsUseCase(darkTheme)
        updateState(_shoppingListStateFlow.value.darkTheme(darkTheme = darkTheme))
    }

    private fun addShoppingList(name: String, icon: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                addShoppingListsUseCase(name = name, icon = icon)
            }.onFailure { error ->
                Log.e(TAG, "error in add shopping list -> $error")
            }
        }
    }

    private fun copyShoppingList(list: ListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                copyShoppingListsUseCase(list)
            }.onFailure { error ->
                Log.e(TAG, "error in copy shopping list -> $error")
            }
        }
    }

    private fun updateShoppingList(list: ListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                updateShoppingListsUseCase(list)
                selectedList(list)
            }.onFailure { error ->
                Log.e(TAG, "error in update shopping list -> $error")
            }
        }
    }

    private fun removeShoppingList(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                removeShoppingListUseCase(id)
            }.onFailure { error ->
                Log.e(TAG, "error in remove shopping list -> $error")
            }
        }
    }

    private fun removeAllShoppingLists() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                removeAllShoppingListsUseCase()
            }.onFailure { error ->
                Log.e(TAG, "error in remove all shopping lists -> $error")
            }
        }
    }

    private fun search(searchQuery: String) {
        clearSearchResults()
        timer.start(parameter = searchQuery)
    }

    private fun selectedList(selectedList: ListItem) {
        updateState(_shoppingListStateFlow.value.selectedList(selectedList))
    }

    private fun clearSearchResults() {
        updateState(_shoppingListStateFlow.value.clearSearchResults())
    }

    private fun doSearch(searchQuery: String?) {
        if (searchQuery.isNullOrEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                showShoppingListByNameUseCase(searchQuery)
                    .collect { results ->
                        withContext(Dispatchers.Main) {
                            processSearchResult(searchQuery = searchQuery, results = results)
                        }
                    }
            }.onFailure { error ->
                Log.e(TAG, "error in search shopping list -> $error")
            }
        }
    }

    private fun processSearchResult(searchQuery: String, results: List<ListItem>) {
        updateState(
            when {
                results.isNotEmpty() -> _shoppingListStateFlow.value.searchResults(searchQuery = searchQuery, results = results)
                else -> _shoppingListStateFlow.value.nothingFound()
            }
        )
    }

    private fun observeShoppingLists() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                showShoppingListsUseCase()
                    .collect {
                        withContext(Dispatchers.Main) {
                            processObserveShoppingListsResult(it)
                        }
                    }
            }.onFailure { error ->
                Log.e(TAG, "error in show shopping list -> $error")
            }
        }
    }

    private fun processObserveShoppingListsResult(list: List<ListItem>) {
        updateState(_shoppingListStateFlow.value.content(list).clearSearchResults())
    }

    private fun updateState(state: ShoppingListState) {
        _shoppingListStateFlow.update {
            state
        }
    }
}