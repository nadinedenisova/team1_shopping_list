package com.practicum.shoppinglist.main.data.impl

import com.practicum.shoppinglist.core.data.SqlDelightDataSource
import com.practicum.shoppinglist.core.data.mapper.toListEntity
import com.practicum.shoppinglist.core.data.mapper.toListItem
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.domain.api.MainScreenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainScreenRepositoryImpl(
    private val dataSource: SqlDelightDataSource,
) : MainScreenRepository {

    override suspend fun showShoppingLists(): Flow<List<ListItem>> {
        return dataSource.getAllLists().map { list ->
            list.map { item ->
                item.toListItem()
            }
        }
    }

    override suspend fun showShoppingListByName(name: String) {
        //TO-DO
    }

    override suspend fun addShoppingList(list: ListItem) {
        dataSource.insertList(list.toListEntity())
    }

    override suspend fun removeAllShoppingLists() {
        //TO-DO
    }

    override suspend fun updateShoppingLIst(list: ListItem) {
        dataSource.updateList(list.toListEntity())
    }
}