package com.practicum.shoppinglist.core.data.mapper

import com.practicum.shoppinglist.ProductEntity
import com.practicum.shoppinglist.core.domain.models.ProductItem

fun ProductEntity.toProductItem() = ProductItem(
    id = id,
    name = name,
    unit = unit,
    count = count.toInt(),
    completed = completed > 0,
)