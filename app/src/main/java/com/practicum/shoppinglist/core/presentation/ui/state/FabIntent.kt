package com.practicum.shoppinglist.core.presentation.ui.state

import androidx.compose.ui.unit.Dp

sealed interface FabIntent {
    data class OpenDetailsBottomSheet(val state: String) : FabIntent
    data object CloseDetailsBottomSheet : FabIntent
    data class OffsetY(val offset: Dp) : FabIntent
    data class AddProduct(val active: Boolean) : FabIntent
    data class EditProduct(val active: Boolean) : FabIntent

    //    data class AddProductItem(
//        val shoppingListId: Long,
//        val productEntity: ProductEntity,
//    )
}