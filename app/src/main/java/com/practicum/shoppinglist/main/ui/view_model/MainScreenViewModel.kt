package com.practicum.shoppinglist.main.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.shoppinglist.common.resources.ShoppingListState
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.domain.api.MainScreenRepository
import com.practicum.shoppinglist.main.domain.impl.AddShoppingListUseCase
import com.practicum.shoppinglist.main.domain.impl.ShowShoppingListsUseCase
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

    private val showShoppingListsUseCase = ShowShoppingListsUseCase(mainScreenRepository)
    private val addShoppingListsUseCase = AddShoppingListUseCase(mainScreenRepository)

    init {
        observeShoppingLists()
    }

    fun addShoppingList(name: String, icon: Long) {
        viewModelScope.launch {
            addShoppingListsUseCase(name, icon)
        }
    }

    private fun observeShoppingLists() {
        viewModelScope.launch {
            showShoppingListsUseCase()
                .collect {
                    processResult(it)
                }
        }
    }

    private fun processResult(list: List<ListItem>) {
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

}