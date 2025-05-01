package com.practicum.shoppinglist.details.presentation.models

sealed interface ProductSort {
    data object Default: ProductSort
    data object ASC: ProductSort
    data class Manual(val order: Map<Long, Int>): ProductSort
}