package com.practicum.shoppinglist.common.resources

import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.details.utils.model.ProductSortOrder

sealed interface DetailsScreenIntent {
    data class Init(val shoppingListId: Long) : DetailsScreenIntent
    data class ToggleProductSheet(val isOpen: Boolean) : DetailsScreenIntent
    object ShowAddProductSheet : DetailsScreenIntent
    object CloseAddProductSheet : DetailsScreenIntent
    object AddUnits : DetailsScreenIntent
    object SubstractUnits : DetailsScreenIntent

    data class EditUnitsCount(val value: Int) : DetailsScreenIntent
    data class EditName(val value: String) : DetailsScreenIntent
    data class EditUnit(val value: String) : DetailsScreenIntent
    data class ToggleCompleted(val currentValue: ProductItem) : DetailsScreenIntent
    data class SelectedProduct(val product: ProductItem) : DetailsScreenIntent
    data object EditProduct : DetailsScreenIntent
    data object AddProduct : DetailsScreenIntent
    data object DeleteAll : DetailsScreenIntent
    data object DeteleCompleted : DetailsScreenIntent

    class SortOrder(val sortOrder: ProductSortOrder) : DetailsScreenIntent
    class UpdateManualSortOrder(val fromIndex: Int, val toIndex: Int) : DetailsScreenIntent
}
