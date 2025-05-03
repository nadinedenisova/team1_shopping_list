package com.practicum.shoppinglist.di

import com.practicum.shoppinglist.main.data.impl.auth.TokenStorageImpl
import com.practicum.shoppinglist.main.domain.api.TokenStorage
import dagger.Binds
import dagger.Module
@Module
abstract class StorageModule {

    @Binds
    abstract fun bindTokenStorage(impl: TokenStorageImpl): TokenStorage
}