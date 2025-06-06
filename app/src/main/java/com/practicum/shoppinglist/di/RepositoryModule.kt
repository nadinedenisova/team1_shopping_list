package com.practicum.shoppinglist.di

import com.practicum.shoppinglist.details.data.DetailsScreenRepositoryImpl
import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository
import com.practicum.shoppinglist.main.data.impl.MainScreenRepositoryImpl
import com.practicum.shoppinglist.main.data.impl.SettingsRepositoryImpl
import com.practicum.shoppinglist.main.data.impl.auth.AuthorizationRepositoryImpl
import com.practicum.shoppinglist.main.domain.api.AuthorizationRepository
import com.practicum.shoppinglist.main.domain.api.MainScreenRepository
import com.practicum.shoppinglist.main.domain.api.SettingsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun MainScreenRepository(
        repository: MainScreenRepositoryImpl
    ): MainScreenRepository

    @Binds
    @Singleton
    abstract fun SettingsRepository(
        repository: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    @Singleton
    abstract fun DetailsScreenRepository(
        repository: DetailsScreenRepositoryImpl
    ): DetailsScreenRepository

    @Binds
    @Singleton
    abstract fun AuthorizationRepository(
        repository: AuthorizationRepositoryImpl
    ): AuthorizationRepository
}