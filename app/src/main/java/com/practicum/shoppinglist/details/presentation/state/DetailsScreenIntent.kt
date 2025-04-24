package com.practicum.shoppinglist.details.presentation.state

sealed interface DetailsScreenIntent {
    data class ToggleProductSheet(val isOpen: Boolean): DetailsScreenIntent
    object ShowAddProductSheet: DetailsScreenIntent
    object CloseAddProductSheet: DetailsScreenIntent
    object AddUnits: DetailsScreenIntent
    object SubstractUnits: DetailsScreenIntent
    data class EditUnits(val value: Int): DetailsScreenIntent
}
