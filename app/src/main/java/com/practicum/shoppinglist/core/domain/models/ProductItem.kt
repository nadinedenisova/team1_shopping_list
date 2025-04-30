package com.practicum.shoppinglist.core.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductItem(
    @SerialName("list_item_id") override val id: Long,
    val name: String,
    val unit: String,
    val count: Int,
    val completed: Boolean,
)  : BaseItem(id)