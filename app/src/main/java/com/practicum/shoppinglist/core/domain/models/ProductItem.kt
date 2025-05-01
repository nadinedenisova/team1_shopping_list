package com.practicum.shoppinglist.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItem(
    val id: Long,
    val name: String,
    val unit: String,
    val count: Int,
    val completed: Boolean,
) : Parcelable
