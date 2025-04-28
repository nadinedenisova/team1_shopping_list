package com.practicum.shoppinglist.core.presentation.ui.state

import androidx.compose.ui.unit.Dp

sealed interface FabIntent {
    data object OpenDetailsBottomSheet : FabIntent
    data object CloseDetailsBottomSheet : FabIntent
    data class OffsetY(val offset: Dp) : FabIntent
    data class AddProduct(val active: Boolean) : FabIntent

    //    data class AddProductItem(
//        val shoppingListId: Long,
//        val productEntity: ProductEntity,
//    )
}