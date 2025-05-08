package com.practicum.shoppinglist.common.resources

import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.details.utils.model.ProductSortOrder

sealed interface DetailsScreenIntent {
    class Init(val shoppingListId: Long) : DetailsScreenIntent
    class ToggleProductSheet(val isOpen: Boolean) : DetailsScreenIntent
    object ShowAddProductSheet : DetailsScreenIntent
    object CloseAddProductSheet : DetailsScreenIntent
    object AddUnits : DetailsScreenIntent
    object SubstractUnits : DetailsScreenIntent

    class EditUnitsCount(val value: Int) : DetailsScreenIntent
    class EditName(val value: String) : DetailsScreenIntent
    class EditUnit(val value: String) : DetailsScreenIntent
    class ToggleCompleted(val currentValue: ProductItem) : DetailsScreenIntent
    class SelectedProduct(val product: ProductItem) : DetailsScreenIntent
    object EditProduct : DetailsScreenIntent
    object AddProduct : DetailsScreenIntent
    object DeleteAll : DetailsScreenIntent
    object DeteleCompleted : DetailsScreenIntent

    class SortOrder(val sortOrder: ProductSortOrder) : DetailsScreenIntent
    class UpdateManualSortOrder(val fromIndex: Int, val toIndex: Int) : DetailsScreenIntent

    class SearchProductHint(val query: String) : DetailsScreenIntent
}
