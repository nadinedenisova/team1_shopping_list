package com.practicum.shoppinglist.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.practicum.shoppinglist.details.presentation.state.DetailsScreenIntent
import com.practicum.shoppinglist.details.presentation.state.DetailsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetailsViewModel : ViewModel() {
    private var _state: MutableStateFlow<DetailsScreenState> =
        MutableStateFlow(DetailsScreenState())
    val state: StateFlow<DetailsScreenState>
        get() = _state.asStateFlow()

    fun onIntent(intent: DetailsScreenIntent) {
        when (intent) {
            DetailsScreenIntent.CloseAddProductSheet -> {
                _state.update { it.copy(showAddProductSheet = false) }
            }

            DetailsScreenIntent.ShowAddProductSheet -> {
                _state.update { it.copy(showAddProductSheet = true) }
            }

            is DetailsScreenIntent.ToggleProductSheet -> {
                _state.update { it.copy(showAddProductSheet = intent.isOpen) }
            }

            DetailsScreenIntent.AddUnits -> {
                _state.update { it.copy(product = it.product.copy(count = it.product.count + 1)) }
            }
            DetailsScreenIntent.SubstractUnits -> {
                _state.update { it.copy(product = it.product.copy(count = it.product.count - 1)) }
            }

            is DetailsScreenIntent.EditUnits -> {
                _state.update { it.copy(product = it.product.copy(count = intent.value)) }
            }
        }
    }
}