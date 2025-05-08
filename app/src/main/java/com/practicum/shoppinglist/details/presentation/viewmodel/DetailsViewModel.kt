package com.practicum.shoppinglist.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.common.resources.DetailsScreenIntent
import com.practicum.shoppinglist.common.resources.ListAction
import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.details.domain.impl.AddItemOrderUseCase
import com.practicum.shoppinglist.details.domain.impl.AddProductUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteAllProductsUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteCompletedProductsUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteProductUseCase
import com.practicum.shoppinglist.details.domain.impl.GetProductListUseCase
import com.practicum.shoppinglist.details.domain.impl.GetProductSortOrderUseCase
import com.practicum.shoppinglist.details.domain.impl.SearchProductHintUseCase
import com.practicum.shoppinglist.details.domain.impl.UpdateProductUseCase
import com.practicum.shoppinglist.details.presentation.state.DetailsScreenState
import com.practicum.shoppinglist.details.presentation.state.DetailsScreenState.Companion.editProduct
import com.practicum.shoppinglist.details.utils.model.ProductSortOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val deleteAllProductsUseCase: DeleteAllProductsUseCase,
    private val deleteCompletedProductsUseCase: DeleteCompletedProductsUseCase,
    private val getProductSortOrderUseCase: GetProductSortOrderUseCase,
    private val addItemOrderUseCase: AddItemOrderUseCase,
    private val searchProductHintUseCase: SearchProductHintUseCase,
) : ViewModel() {

    private var _state: MutableStateFlow<DetailsScreenState> =
        MutableStateFlow(DetailsScreenState())
    val state: StateFlow<DetailsScreenState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<ListAction>()
    val action: SharedFlow<ListAction> = _action

    private var searchHintJob: Job? = null

    @OptIn(FlowPreview::class)
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
                _state.update {
                    it.copy(product = it.product.copy(count = it.product.count + 1))
                }
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
                        item = ProductItem(
                            name = _state.value.product.name,
                            unit = _state.value.product.unit,
                            count = _state.value.product.count,
                            completed = false
                        )
                    )
                    _state.update { it.copy(product = ProductItem()) }
                }
            }

            is DetailsScreenIntent.SelectedProduct -> {
                _state.update {
                    it.editProduct(intent.product)
                }
            }

            is DetailsScreenIntent.EditProduct -> {
                viewModelScope.launch(Dispatchers.IO) {
                    updateProductUseCase(_state.value.product)
                }
            }

            is DetailsScreenIntent.EditName -> {
                _state.update { it.copy(product = it.product.copy(name = intent.value)) }
            }

            is DetailsScreenIntent.ToggleCompleted -> {
                viewModelScope.launch(Dispatchers.IO) {
                    updateProductUseCase(
                        intent.currentValue.copy(completed = !intent.currentValue.completed)
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

                val updated = _state.value.productList.toMutableList()
                    .apply { add(intent.toIndex, removeAt(intent.fromIndex)) }

                _state.update {
                    it.copy(productList = updated)
                }

                viewModelScope.launch(Dispatchers.IO) {
                    addItemOrderUseCase(
                        _state.value.shoppingListId,
                        _state.value.productList.mapIndexed { index, item -> item.id to index.toLong() }
                            .toMap()
                    )
                }
            }

            BaseIntent.QueryRemoveShoppingList -> {
                viewModelScope.launch {
                    _action.emit(ListAction.RemoveItem)
                }
            }

            is BaseIntent.RemoveListItem -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteProductUseCase(intent.id)
                }
            }

            is DetailsScreenIntent.SearchProductHint -> {
                searchHintJob?.cancel()

                searchHintJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(300L)
                    val hintList = searchProductHintUseCase.invoke(intent.query).first()
                    _state.update {
                        it.copy(
                            productMenuList = hintList
                        )
                    }
                }
            }
        }
    }

    private fun observeProductList() {
        viewModelScope.launch(Dispatchers.IO) {
            getProductListUseCase.invoke(_state.value.shoppingListId)
                .transform {
                    val sortedItems = when (_state.value.sortOrderMode) {
                        ProductSortOrder.Default -> it
                        is ProductSortOrder.ASC -> it.sortedBy { item -> item.name }
                        is ProductSortOrder.Manual -> {
                            val sortOrder =
                                getProductSortOrderUseCase.invoke(_state.value.shoppingListId)
                                    .firstOrNull()
                            if (!sortOrder.isNullOrEmpty()) {
                                it.sortedBy { item -> sortOrder[item.id] }
                            } else {
                                it
                            }
                        }

                    }
                    emit(sortedItems)
                }
                .collect { productList ->
                    _state.update {
                        it.copy(
                            productList = productList,
                        )
                    }
                }
        }
    }
}