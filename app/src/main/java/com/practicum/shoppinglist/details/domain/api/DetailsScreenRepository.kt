package com.practicum.shoppinglist.details.domain.api

import com.practicum.shoppinglist.ProductSortEntity
import com.practicum.shoppinglist.core.domain.models.ProductItem
import kotlinx.coroutines.flow.Flow

interface DetailsScreenRepository {
    suspend fun addProduct(shoppingListId: Long, item: ProductItem): Long
    suspend fun removeProduct(id: Long): Long
    fun getAllProduct(shoppingListId: Long): Flow<List<ProductItem>>
    suspend fun updateProduct(item: ProductItem): ProductItem
    suspend fun removeAllProducts(shoppingListId: Long): Long
    suspend fun removeCompletedProducts(shoppingListId: Long): Long
    fun getSortOrderByShoppingListId(shoppingListId: Long): Flow<List<ProductSortEntity>>
    suspend fun addItemSortOrder(shoppingListId: Long, itemSortOrder: Map<Long, Long>): Long
    fun searchProductHint(query: String): Flow<List<String>>
}