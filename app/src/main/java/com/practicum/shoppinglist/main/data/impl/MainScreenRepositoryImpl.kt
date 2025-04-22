package com.practicum.shoppinglist.main.data.impl

import android.content.Context
import android.content.SharedPreferences
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.core.data.SqlDelightDataSource
import com.practicum.shoppinglist.core.data.mapper.toListEntity
import com.practicum.shoppinglist.core.data.mapper.toListItem
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.domain.api.MainScreenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import androidx.core.content.edit

class MainScreenRepositoryImpl @Inject constructor(
    context: Context,
    private val prefs: SharedPreferences,
    private val dataSource: SqlDelightDataSource,
) : MainScreenRepository {

    private val key = context.getString(R.string.theme_save_key)

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
        //TO-DO
    }

    override suspend fun updateShoppingLIst(list: ListItem) {
        dataSource.updateList(list.toListEntity())
    }

    override fun getThemeSettings(): Boolean {
        return prefs.getBoolean(key, false)
    }

    override fun changeThemeChange(darkTheme: Boolean) {
        prefs.edit {
            putBoolean(key, darkTheme)
        }
    }
}