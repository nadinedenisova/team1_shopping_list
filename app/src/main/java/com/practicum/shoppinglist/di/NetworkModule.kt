package com.practicum.shoppinglist.di

import com.android.shoppinglist.feature.http.data.network.AuthResponse
import com.practicum.shoppinglist.core.data.network.HttpNetworkClient
import com.practicum.shoppinglist.main.data.impl.auth.AuthorizationKtorNetworkClient
import com.practicum.shoppinglist.main.data.impl.auth.dto.AuthRequest
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpNetworkClient<AuthRequest, AuthResponse> {
        return AuthorizationKtorNetworkClient()
    }
}