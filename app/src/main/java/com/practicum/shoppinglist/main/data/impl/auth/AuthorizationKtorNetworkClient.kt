package com.practicum.shoppinglist.main.data.impl.auth

import com.android.shoppinglist.feature.http.data.network.AuthResponse
import com.practicum.shoppinglist.core.data.network.HttpKtorNetworkClient
import com.practicum.shoppinglist.main.data.impl.auth.dto.AuthRequest
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class AuthorizationKtorNetworkClient : HttpKtorNetworkClient<AuthRequest, AuthResponse>() {
    override suspend fun sendResponseByType(request: AuthRequest): HttpResponse {
        return httpClient.get(BASE_URL) {
            when (request) {
                AuthRequest.Login -> TODO()
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: AuthRequest,
        httpResponse: HttpResponse
    ): AuthResponse {
        return when (requestType) {
            AuthRequest.Login -> TODO()
        }
    }

    private companion object {
        const val BASE_URL = "https:/test.url/"
    }
}