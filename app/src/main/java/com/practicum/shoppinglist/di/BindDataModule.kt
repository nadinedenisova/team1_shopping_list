package com.practicum.shoppinglist.di

import com.practicum.shoppinglist.core.data.LocalDataSource
import com.practicum.shoppinglist.core.data.SqlDelightDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class BindDataModule {

    @Binds
    @Singleton
    abstract fun provideSqlDelightDataSource(
        dataSource: SqlDelightDataSource
    ): LocalDataSource
}