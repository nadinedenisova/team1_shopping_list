package com.practicum.shoppinglist.core.presentation.ui.state

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class FabState (
    val isOpenDetailsBottomSheetState: Boolean = false,
    val offsetY: Dp = 0.dp,
    val addProduct: Boolean = false,
)
