package com.practicum.shoppinglist.common.utils

import androidx.compose.runtime.saveable.Saver
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

fun <T> itemSaver(serializer: KSerializer<T>): Saver<T?, String> {
    return Saver(
        save = { item ->
            item?.let { Json.encodeToString(serializer, it) } ?: ""
        },
        restore = { saved ->
            if (saved.isNotEmpty()) Json.decodeFromString(serializer, saved) else null
        }
    )
}