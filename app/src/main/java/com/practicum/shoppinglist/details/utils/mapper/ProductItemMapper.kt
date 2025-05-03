package com.practicum.shoppinglist.details.utils.mapper

import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.details.presentation.models.ProductItemUiModel

fun ProductItem.toProductItemUi() = ProductItemUiModel(
    id = id,
    name = name,
    amount = "$count $unit",
    completed = completed
)