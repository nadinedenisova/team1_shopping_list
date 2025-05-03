package com.practicum.shoppinglist.common.utils

import kotlinx.coroutines.delay
import java.sql.SQLException

suspend fun <T> withRetry(
    times: Int = 1,
    delayMs: Long,
    onError: (e: Exception) -> T,
    body: suspend () -> T
): T {
    repeat(times) {
        try {
            return body()
        } catch (e: Exception) {
            if (e is SQLException) {
                delay(delayMs)
            } else {
                return onError(e)
            }
        }
    }
    return onError(Exception("Something went wrong"))
}
