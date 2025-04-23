package com.practicum.shoppinglist.core.data

import com.practicum.shoppinglist.ListEntity
import com.practicum.shoppinglist.ProductEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllLists(): Flow<List<ListEntity>>
    suspend fun insertList(name: String, icon: Long): Long
    suspend fun updateList(item: ListEntity): Long
    suspend fun getListById(id: Long): ListEntity?
    suspend fun deleteListById(id: Long): Long
    fun searchListByName(name: String): Flow<List<ListEntity>>

    fun getProductsByListId(id: Long): Flow<List<ProductEntity>>
    suspend fun getProductById(id: Long): ProductEntity?
    suspend fun insertProduct(listId: Long, item: ProductEntity)
    suspend fun updateProduct(item: ProductEntity): Long
    suspend fun deleteProductById(id: Long): Long
}