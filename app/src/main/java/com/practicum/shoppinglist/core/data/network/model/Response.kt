package com.practicum.shoppinglist.core.data.network.model

open class Response<T>(
    var isSuccess: Boolean = false,
    var resultCode: StatusCode = StatusCode(0),
    var body: T? = null
)