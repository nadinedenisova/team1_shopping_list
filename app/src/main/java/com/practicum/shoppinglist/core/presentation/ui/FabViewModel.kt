package com.practicum.shoppinglist.core.presentation.ui

import androidx.lifecycle.ViewModel
import com.practicum.shoppinglist.core.presentation.ui.state.FabIntent
import com.practicum.shoppinglist.core.presentation.ui.state.FabState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FabViewModel @Inject constructor() : ViewModel() {
    private var _fabState: MutableStateFlow<FabState> = MutableStateFlow(FabState())
    val fabState: StateFlow<FabState>
        get() = _fabState.asStateFlow()

    fun onIntent(intent: FabIntent) {
        when(intent) {
            FabIntent.CloseDetailsBottomSheet -> { _fabState.update { it.copy(isOpenDetailsBottomSheetState = null) } }
            is FabIntent.OpenDetailsBottomSheet -> { _fabState.update { it.copy(isOpenDetailsBottomSheetState = intent.state) } }
            is FabIntent.OffsetY -> { _fabState.update { it.copy(offsetY = intent.offset) } }
            is FabIntent.AddProduct -> { _fabState.update { it.copy(addProduct = intent.active) } }
            is FabIntent.EditProduct -> { _fabState.update { it.copy(editProduct = intent.active) } }
        }
    }
}