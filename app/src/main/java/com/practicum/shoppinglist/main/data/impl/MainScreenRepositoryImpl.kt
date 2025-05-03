package com.practicum.shoppinglist.main.data.impl

import com.practicum.shoppinglist.core.data.SqlDelightDataSource
import com.practicum.shoppinglist.core.data.mapper.toListEntity
import com.practicum.shoppinglist.core.data.mapper.toListItem
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.domain.api.MainScreenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainScreenRepositoryImpl @Inject constructor(
    private val dataSource: SqlDelightDataSource,
) : MainScreenRepository {

    override suspend fun showShoppingLists(): Flow<List<ListItem>> {
        return dataSource.getAllLists().map { list ->
            list.map { item ->
                item.toListItem()
            }
        }
    }

    override suspend fun showShoppingListByName(name: String): Flow<List<ListItem>> {
        return dataSource.searchListByName(name).map { list ->
            list.map { item ->
                item.toListItem()
            }
        }
    }

    override suspend fun addShoppingList(name: String, icon: Long) {
        dataSource.insertList(name, icon)
    }

    override suspend fun removeShoppingList(id: Long) {
        dataSource.deleteListById(id)
    }

    override suspend fun removeAllShoppingLists() {
        dataSource.deleteAll()
    }

    override suspend fun updateShoppingLIst(list: ListItem) {
        dataSource.updateList(list.toListEntity())
    }
}