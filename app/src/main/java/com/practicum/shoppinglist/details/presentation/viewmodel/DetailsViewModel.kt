package com.practicum.shoppinglist.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.shoppinglist.ProductEntity
import com.practicum.shoppinglist.core.data.mapper.toProductEntity
import com.practicum.shoppinglist.details.domain.impl.AddItemOrderUseCase
import com.practicum.shoppinglist.details.domain.impl.AddProductUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteAllProductsUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteCompletedProductsUseCase
import com.practicum.shoppinglist.details.domain.impl.GetProductListUseCase
import com.practicum.shoppinglist.details.domain.impl.GetProductSortOrderUseCase
import com.practicum.shoppinglist.details.domain.impl.UpdateProductUseCase
import com.practicum.shoppinglist.details.presentation.models.ProductDetails
import com.practicum.shoppinglist.details.presentation.state.DetailsScreenIntent
import com.practicum.shoppinglist.details.presentation.state.DetailsScreenState
import com.practicum.shoppinglist.details.utils.model.ProductSortOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteAllProductsUseCase: DeleteAllProductsUseCase,
    private val deleteCompletedProductsUseCase: DeleteCompletedProductsUseCase,
    private val getProductSortOrderUseCase: GetProductSortOrderUseCase,
    private val addItemOrderUseCase: AddItemOrderUseCase,
) : ViewModel() {
    private var _state: MutableStateFlow<DetailsScreenState> =
        MutableStateFlow(DetailsScreenState())
    val state: StateFlow<DetailsScreenState>
        get() = _state.asStateFlow()

    fun onIntent(intent: DetailsScreenIntent) {
        when (intent) {
            DetailsScreenIntent.CloseAddProductSheet -> {
                _state.update { it.copy(showAddProductSheet = false) }
            }

            DetailsScreenIntent.ShowAddProductSheet -> {
                _state.update { it.copy(showAddProductSheet = true) }
            }

            is DetailsScreenIntent.ToggleProductSheet -> {
                _state.update { it.copy(showAddProductSheet = intent.isOpen) }
            }

            DetailsScreenIntent.AddUnits -> {
                _state.update { it.copy(product = it.product.copy(count = it.product.count + 1)) }
            }

            DetailsScreenIntent.SubstractUnits -> {
                _state.update { it.copy(product = it.product.copy(count = it.product.count - 1)) }
            }

            is DetailsScreenIntent.EditUnitsCount -> {
                _state.update { it.copy(product = it.product.copy(count = intent.value)) }
            }

            is DetailsScreenIntent.Init -> {
                _state.update { it.copy(shoppingListId = intent.shoppingListId) }
                observeProductList()
            }

            DetailsScreenIntent.AddProduct -> {
                viewModelScope.launch(Dispatchers.IO) {
                    addProductUseCase(
                        shoppingListId = _state.value.shoppingListId,
                        item = ProductEntity(
                            id = -1,
                            name = _state.value.product.name,
                            unit = _state.value.product.unit,
                            count = _state.value.product.count.toLong(),
                            completed = 0
                        )
                    )
                    _state.update { it.copy(product = ProductDetails()) }
                }
            }

            is DetailsScreenIntent.EditName -> {
                _state.update { it.copy(product = it.product.copy(name = intent.value)) }
            }

            is DetailsScreenIntent.ToggleCompleted -> {
                viewModelScope.launch(Dispatchers.IO) {
                    updateProductUseCase(
                        intent.currentValue.copy(completed = !intent.currentValue.completed)
                            .toProductEntity()
                    )
                }
            }

            DetailsScreenIntent.DeleteAll -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteAllProductsUseCase(_state.value.shoppingListId)
                }
            }

            DetailsScreenIntent.DeteleCompleted -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteCompletedProductsUseCase(_state.value.shoppingListId)
                }
            }

            is DetailsScreenIntent.EditUnit -> {
                _state.update { it.copy(product = it.product.copy(unit = intent.value)) }
            }

            is DetailsScreenIntent.SortOrder -> {
                _state.update { it.copy(sortOrderMode = intent.sortOrder) }
            }

            is DetailsScreenIntent.UpdateManualSortOrder -> {
                viewModelScope.launch(Dispatchers.IO) {
                    addItemOrderUseCase(_state.value.shoppingListId, intent.sortOrder)
                }
            }
        }
    }

    private fun observeProductList() {
        viewModelScope.launch(Dispatchers.IO) {
            getProductSortOrderUseCase.invoke(_state.value.shoppingListId).collect { sortOrder ->
                _state.update { it.copy(sortOrder = sortOrder) }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            getProductListUseCase.invoke(_state.value.shoppingListId).transform {
                val sortedItems = when (_state.value.sortOrderMode) {
                    is ProductSortOrder.ASC -> it.sortedBy { item -> item.name }
                    is ProductSortOrder.Manual -> {
                        if (_state.value.sortOrder.isNotEmpty()) {
                            it.sortedBy { item -> _state.value.sortOrder[item.id] }
                        } else {
                            it
                        }
                    }
                }
                emit(sortedItems)
            }.collect { productList ->
                _state.update {
                    it.copy(
                        productList = productList,
                        productMenuList = productList.map { it.name })
                }
            }
        }
    }
}