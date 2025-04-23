package com.practicum.shoppinglist.details.presentation.state

import com.practicum.shoppinglist.details.presentation.models.ProductDetails

data class DetailsScreenState(
    val showAddProductSheet: Boolean = false,
    val unitList: List<String> = emptyList(),
    val productList: List<String> = emptyList(),
    val product: ProductDetails = ProductDetails()
)