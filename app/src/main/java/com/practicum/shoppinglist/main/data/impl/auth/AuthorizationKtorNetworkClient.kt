package com.practicum.shoppinglist.main.data.impl.auth

import com.android.shoppinglist.feature.http.data.network.AuthResponse
import com.practicum.shoppinglist.core.data.network.HttpKtorNetworkClient
import com.practicum.shoppinglist.core.data.network.model.HttpMethodType
import com.practicum.shoppinglist.main.data.impl.auth.dto.AuthRequest
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.path

class AuthorizationKtorNetworkClient : HttpKtorNetworkClient<AuthRequest, AuthResponse>() {

    override suspend fun sendResponseByType(
        httpMethod: HttpMethodType,
        request: AuthRequest
    ): HttpResponse {
        return when (httpMethod) {
            HttpMethodType.GET -> httpClient.get(BASE_URL) {
                configureUrl(request)
                headers(request)
            }
            HttpMethodType.POST -> httpClient.post(BASE_URL) {
                configureUrl(request)
                contentType(ContentType.Application.Json)
                setBody(request)
                headers(request)
            }
        }
    }

    private fun HttpRequestBuilder.configureUrl(request: AuthRequest) {
        url {
            when (request) {
                is AuthRequest.Registration -> path("auth/registration")
                is AuthRequest.Login -> path("auth/login")
                is AuthRequest.RefreshToken -> path("auth/refresh")
                is AuthRequest.Validation -> path("auth/check")
                is AuthRequest.Recovery -> path("auth/recovery")
            }
        }
    }

    private fun HttpRequestBuilder.headers(request: AuthRequest) {
        url {
            when (request) {
                is AuthRequest.Registration -> {
                    headers {
                        append("Custom-Header", "CustomValue")
                    }
                }
                is AuthRequest.Login -> {
                    headers {
                        append("Custom-Header", "CustomValue")
                    }
                }
                is AuthRequest.RefreshToken -> {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer " + request.token)
                        append("Custom-Header", "CustomValue")
                    }
                }
                is AuthRequest.Validation -> {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer " + request.token)
                        append("Custom-Header", "CustomValue")
                    }
                }
                is AuthRequest.Recovery -> {
                    headers {}
                }
            }
        }
    }

    override suspend fun getResponseBodyByRequestType(
        requestType: AuthRequest,
        httpResponse: HttpResponse
    ): AuthResponse {
        return when (requestType) {
            is AuthRequest.Login -> {
                httpResponse.body<AuthResponse.Login>()
            }

            is AuthRequest.Validation -> {
                httpResponse.body<AuthResponse.Validation>()
            }

            is AuthRequest.RefreshToken -> {
                httpResponse.body<AuthResponse.Refresh>()
            }

            is AuthRequest.Registration -> {
                httpResponse.body<AuthResponse.Registration>()
            }

            is AuthRequest.Recovery -> AuthResponse.Recovery()
        }
    }

    private companion object {
        const val BASE_URL = "http://130.193.44.66:8080/"
    }
}