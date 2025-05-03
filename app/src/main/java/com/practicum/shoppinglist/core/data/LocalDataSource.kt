package com.practicum.shoppinglist.core.data

import com.practicum.shoppinglist.ListEntity
import com.practicum.shoppinglist.ProductEntity
import com.practicum.shoppinglist.ProductSortEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllLists(): Flow<List<ListEntity>>
    suspend fun insertList(name: String, icon: Long)
    suspend fun updateList(item: ListEntity)
    suspend fun getListById(id: Long): ListEntity?
    suspend fun deleteListById(id: Long)
    fun searchListByName(name: String): Flow<List<ListEntity>>

    fun getProductsByListId(id: Long): Flow<List<ProductEntity>>
    suspend fun getProductById(id: Long): ProductEntity?
    suspend fun insertProduct(listId: Long, item: ProductEntity)
    suspend fun updateProduct(item: ProductEntity)
    suspend fun deleteProductById(id: Long)
    suspend fun deleteAll(): Long
    suspend fun deleteAllByListId(id: Long): Long
    suspend fun deleteAllCompletedByListId(id: Long): Long

    fun getSortOrderByListId(listId: Long): Flow<List<ProductSortEntity>>
    suspend fun addProductSortOrder(listId: Long, sortOrder: Map<Long, Long>): Long
}