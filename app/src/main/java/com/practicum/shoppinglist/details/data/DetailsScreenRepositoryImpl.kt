package com.practicum.shoppinglist.details.data

import com.practicum.shoppinglist.ProductEntity
import com.practicum.shoppinglist.ProductSortEntity
import com.practicum.shoppinglist.core.data.SqlDelightDataSource
import com.practicum.shoppinglist.core.data.mapper.toProductItem
import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetailsScreenRepositoryImpl @Inject constructor(
    private val dataSource: SqlDelightDataSource,
) : DetailsScreenRepository {
    override suspend fun addProduct(shoppingListId: Long, item: ProductEntity): Long {
        dataSource.insertProduct(shoppingListId, item)
        return -1
    }

    override suspend fun removeProduct(id: Long): Long {
        dataSource.deleteProductById(id)
        return -1
    }

    override suspend fun getAllProduct(shoppingListId: Long): Flow<List<ProductItem>> =
        dataSource.getProductsByListId(shoppingListId)
            .map { it.map { item -> item.toProductItem() } }

    override suspend fun updateProduct(item: ProductEntity): ProductItem {
        dataSource.updateProduct(item)
        return item.toProductItem()
    }

    override suspend fun removeAllProducts(shoppingListId: Long): Long {
        return dataSource.deleteAllByListId(shoppingListId)
    }

    override suspend fun removeCompletedProducts(shoppingListId: Long): Long {
        return dataSource.deleteAllCompletedByListId(shoppingListId)
    }

    override fun getSortOrderByShoppingListId(shoppingListId: Long): Flow<List<ProductSortEntity>> {
        return dataSource.getSortOrderByListId(shoppingListId)
    }

    override suspend fun addItemSortOrder(
        shoppingListId: Long,
        itemSortOrder: Map<Long, Long>
    ): Long {
        return dataSource.addProductSortOrder(shoppingListId, itemSortOrder)
    }
}