package com.practicum.shoppinglist.core.presentation.ui.state

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class FabState (
    val isOpenDetailsBottomSheetState: String? = null,
    val offsetY: Dp = 0.dp,
    val addProduct: Boolean = false,
    val editProduct: Boolean = false,
) {

    enum class State {
        AddProduct,
        EditProduct,
    }
}
