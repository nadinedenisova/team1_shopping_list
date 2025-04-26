package com.practicum.shoppinglist.core.data.network.model

import com.practicum.shoppinglist.core.domain.models.network.ErrorType

open class Response<T>(
    var isSuccess: Boolean = false,
    var resultCode: StatusCode = StatusCode(0),
    var body: T? = null
)

fun StatusCode.mapToErrorType(): ErrorType {
    return when (code) {
        -1 -> ErrorType.NO_CONNECTION
        201 -> ErrorType.CREATED
        400 -> ErrorType.BAD_REQUEST
        409 -> ErrorType.CONFLICT
        500 -> ErrorType.SERVER_ERROR
        else -> ErrorType.UNKNOWN_ERROR
    }
}