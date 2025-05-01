package com.practicum.shoppinglist.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ProductItem(
    @SerialName("list_item_id") override val id: Long = -1,
    val name: String = "",
    val unit: String = "",
    val count: Int = 0,
    val completed: Boolean = false,
)  : BaseItem(id), Parcelable