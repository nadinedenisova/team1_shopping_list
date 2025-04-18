package com.practicum.shoppinglist.core.data.network

import io.ktor.http.HttpStatusCode

data class HttpResponse(
    val statusCode: HttpStatusCode,
    val body: String
)

interface NetworkClient {
    suspend fun get(url: String): HttpResponse
    suspend fun post(url: String, body: Any): HttpResponse
    suspend fun put(url: String, body: Any): HttpResponse
    suspend fun delete(url: String): HttpResponse
}
