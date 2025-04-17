package com.practicum.shoppinglist.core.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.practicum.shoppinglist.ListEntity
import com.practicum.shoppinglist.ProductEntity
import com.practicum.shoppinglist.ShoppingListDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SqlDelightDataSource @Inject constructor(
    private val db: ShoppingListDatabase,
) : LocalDataSource {
    override fun getAllLists(): Flow<List<ListEntity>> {
        return db.listEntityQueries.getAll().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun insertList(name: String, icon: Long) = withContext(Dispatchers.IO) {
        db.listEntityQueries.insert(
            name = name,
            icon_res_id = icon,
        )
    }

    override suspend fun updateList(item: ListEntity) = withContext(Dispatchers.IO) {
        db.listEntityQueries.update(
            name = item.name,
            icon_res_id = item.icon_res_id,
            id = item.id
        )
    }

    override suspend fun getListById(id: Long): ListEntity? = withContext(Dispatchers.IO) {
        db.listEntityQueries.getById(id).executeAsOneOrNull()
    }

    override suspend fun deleteListById(id: Long) = withContext(Dispatchers.IO) {
        db.listEntityQueries.deleteById(id)
    }

    override fun getProductsByListId(id: Long): Flow<List<ProductEntity>> {
        return db.productEntityQueries.getAllByListId(id).asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun getProductById(id: Long): ProductEntity? = withContext(Dispatchers.IO) {
        db.productEntityQueries.getById(id).executeAsOneOrNull()
    }

    override suspend fun insertProduct(listId: Long, item: ProductEntity) = withContext(Dispatchers.IO) {
        db.productEntityQueries.insert(
            list_id = listId,
            name = item.name,
            unit = item.unit,
            count = item.count,
            completed = item.completed,
        )
    }

    override suspend fun updateProduct(item: ProductEntity) = withContext(Dispatchers.IO) {
        db.productEntityQueries.update(
            id = item.id,
            name = item.name,
            unit = item.unit,
            count = item.count,
            completed = item.completed,
        )
    }

    override suspend fun deleteProductById(id: Long) = withContext(Dispatchers.IO) {
        db.productEntityQueries.deleteById(id)
    }
}