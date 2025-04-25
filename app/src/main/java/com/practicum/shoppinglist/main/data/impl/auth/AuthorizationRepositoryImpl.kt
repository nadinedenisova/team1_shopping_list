package com.practicum.shoppinglist.main.data.impl.auth

import com.android.ktorsample.feature.http.data.network.AuthResponse
import com.android.ktorsample.feature.http.domain.api.AuthorizationRepository
import com.practicum.shoppinglist.core.data.network.HttpNetworkClient
import com.practicum.shoppinglist.main.data.impl.auth.dto.AuthRequest

class AuthorizationRepositoryImpl(
    private val httpNetworkClient: HttpNetworkClient<AuthRequest, AuthResponse>
): AuthorizationRepository {

}