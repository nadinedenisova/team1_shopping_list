package com.practicum.shoppinglist.core.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseItem(
    @SerialName("base_id")open val id: Long,
)