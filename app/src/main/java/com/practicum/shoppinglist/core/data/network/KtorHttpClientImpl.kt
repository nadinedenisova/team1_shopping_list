package com.practicum.shoppinglist.core.data.network
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json

class KtorHttpClientImpl : NetworkClient {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun get(url: String): HttpResponse {
        val response = client.get(url) {}
        return HttpResponse(response.status, response.bodyAsText())
    }

    override suspend fun post(url: String, body: Any): HttpResponse {
        val response = client.post(url) {
            setBody(body)
        }
        return HttpResponse(response.status, response.bodyAsText())
    }

    override suspend fun put(url: String, body: Any): HttpResponse {
        val response = client.put(url) {
            setBody(body)
        }
        return HttpResponse(response.status, response.bodyAsText())
    }

    override suspend fun delete(url: String): HttpResponse {
        val response = client.delete(url)
        return HttpResponse(response.status, response.bodyAsText())
    }

    fun close() {
        client.close()
    }

}