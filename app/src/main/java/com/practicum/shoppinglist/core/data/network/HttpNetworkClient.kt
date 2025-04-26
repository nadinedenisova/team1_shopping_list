package com.practicum.shoppinglist.core.data.network

import com.practicum.shoppinglist.core.data.network.model.HttpMethodType
import com.practicum.shoppinglist.core.data.network.model.Response

interface HttpNetworkClient<T, R> {
    suspend fun getResponse(httpMethod: HttpMethodType, sealedRequest: T): Response<R>
}