package com.practicum.shoppinglist.details.presentation.state

import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.details.presentation.models.ProductDetails
import com.practicum.shoppinglist.details.utils.model.ProductSortOrder

data class DetailsScreenState(
    val shoppingListId: Long = -1,
    val showAddProductSheet: Boolean = false,
    val productList: List<ProductItem> = emptyList(),
    val unitList: List<String> = emptyList(),
    val productMenuList: List<String> = emptyList(),
    val product: ProductDetails = ProductDetails(),
    val sortOrderMode: ProductSortOrder = ProductSortOrder.ASC,
    val sortOrder: Map<Long, Long> = emptyMap()
)