package com.practicum.shoppinglist.details.utils.model

import com.practicum.shoppinglist.R

sealed class ProductSortOrder(val name: Int = -1, val leadingIcon: Int = -1) {
    object Default: ProductSortOrder()
    object ASC: ProductSortOrder(name = R.string.sort_alphabet_order, leadingIcon = R.drawable.ic_alphabet_order)
    object Manual: ProductSortOrder(name = R.string.sort_user_defined, leadingIcon = R.drawable.ic_user_defined)
}