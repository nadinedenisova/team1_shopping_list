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

fun ProductItem.toProductEntity() = ProductEntity(
    id = id,
    name = name,
    unit = unit,
    count = count.toLong(),
    completed = if (completed) 1 else 0,
)