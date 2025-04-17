package com.practicum.shoppinglist.di

import com.practicum.shoppinglist.core.data.LocalDataSource
import com.practicum.shoppinglist.core.data.SqlDelightDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideSqlDelightDataSource(
        dataSource: SqlDelightDataSource
    ): LocalDataSource

    /*@Provides
    @Singleton
    fun provideSharedPreferences(
        context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.prefs_file_name),
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }*/

}