package com.practicum.shoppinglist.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.practicum.shoppinglist.ShoppingListDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideShoppingListDatabase(
        context: Context
    ): ShoppingListDatabase {
        return ShoppingListDatabase(
            driver = AndroidSqliteDriver(
                ShoppingListDatabase.Schema,
                context,
                "ShoppingListDatabase.db",
                callback = object : AndroidSqliteDriver.Callback(ShoppingListDatabase.Schema) {

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        db.setForeignKeyConstraintsEnabled(true)
                    }
                },
            ),
        )
    }
}