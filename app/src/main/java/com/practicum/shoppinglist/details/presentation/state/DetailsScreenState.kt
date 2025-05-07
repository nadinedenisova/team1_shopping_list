package com.practicum.shoppinglist.details.presentation.state

import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.details.utils.model.ProductSortOrder

data class DetailsScreenState(
    val shoppingListId: Long = -1,
    val productId: Long = -1,
    val showAddProductSheet: Boolean = false,
    val productList: List<ProductItem> = emptyList(),
    val unitList: List<String> = emptyList(),
    val productMenuList: List<String> = emptyList(),
    val product: ProductItem = ProductItem(),
    val sortOrderMode: ProductSortOrder = ProductSortOrder.Default,
) {
    companion object {
        fun DetailsScreenState.editProduct(product: ProductItem) = this.copy(product = product)
    }
}