package com.practicum.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.practicum.shoppinglist.MainScreenViewModel
import com.practicum.shoppinglist.di.api.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    abstract fun bindMainActivityViewModel(myViewModel: MainScreenViewModel): ViewModel

}