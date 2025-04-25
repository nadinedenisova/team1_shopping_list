package com.practicum.shoppinglist.core.domain.models

import androidx.compose.runtime.saveable.Saver
import com.google.gson.Gson

data class ListItem(
    val id: Long,
    val name: String,
    val iconResId: Int,
)

val gson = Gson()

val listItemSaver = Saver<ListItem?, String>(
    save = { item ->
        item?.let { gson.toJson(it) } ?: ""
    },
    restore = { saved ->
        if (saved.isNotEmpty()) gson.fromJson(saved, ListItem::class.java) else null
    }
)