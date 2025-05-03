package com.practicum.shoppinglist.core.domain.models

data class ListItem(
    override val id: Long = -1,
    val name: String = "",
    val iconResId: Int = -1,
) : BaseItem(id)