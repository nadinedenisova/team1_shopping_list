package com.practicum.shoppinglist.core.domain.models

data class ProductItem(
    val id: Long,
    val name: String,
    val unit: String,
    val count: Int,
    val completed: Boolean,
)
