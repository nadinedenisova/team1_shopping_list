package com.practicum.shoppinglist.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItem(
    override val id: Long = -1,
    val name: String = "",
    val unit: String = "",
    val count: Int = 0,
    val completed: Boolean = false,
)  : BaseItem(id), Parcelable