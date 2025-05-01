package com.practicum.shoppinglist.details.domain.api

import com.practicum.shoppinglist.ProductEntity
import com.practicum.shoppinglist.ProductSortEntity
import com.practicum.shoppinglist.core.domain.models.ProductItem
import kotlinx.coroutines.flow.Flow

interface DetailsScreenRepository {
    suspend fun addProduct(shoppingListId: Long, item: ProductEntity): Long
    suspend fun removeProduct(id: Long): Long
    suspend fun getAllProduct(shoppingListId: Long): Flow<List<ProductItem>>
    suspend fun updateProduct(item: ProductEntity): ProductItem
    suspend fun removeAllProducts(shoppingListId: Long): Long
    suspend fun removeCompletedProducts(shoppingListId: Long): Long
    fun getSortOrderByShoppingListId(shoppingListId: Long): Flow<List<ProductSortEntity>>
    suspend fun addItemSortOrder(shoppingListId: Long, itemSortOrder: Map<Long, Long>): Long
}