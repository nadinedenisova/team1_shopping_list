package com.practicum.shoppinglist.core.data.mapper

import com.practicum.shoppinglist.ListEntity
import com.practicum.shoppinglist.core.domain.models.ListItem

fun ListEntity.toListItem(): ListItem = ListItem(
    id = id,
    name = name,
    iconResId = icon_res_id.toInt()
)

fun ListItem.toListEntity(): ListEntity = ListEntity(
    id = id,
    name = name,
    icon_res_id = iconResId.toLong()
)