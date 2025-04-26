package com.practicum.shoppinglist.di

import com.practicum.shoppinglist.core.data.network.KtorHttpClientImpl
import com.practicum.shoppinglist.core.data.network.NetworkClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): NetworkClient {
        return KtorHttpClientImpl()
    }
}