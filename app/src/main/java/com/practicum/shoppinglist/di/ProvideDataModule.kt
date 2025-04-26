package com.practicum.shoppinglist.di

import android.content.Context
import android.content.SharedPreferences
import com.practicum.shoppinglist.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProvideDataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.prefs_file_name),
            Context.MODE_PRIVATE
        )
    }


}