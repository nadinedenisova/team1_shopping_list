package com.practicum.shoppinglist.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.shoppinglist.ProductEntity
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.common.resources.DetailsScreenIntent
import com.practicum.shoppinglist.common.resources.ListAction
import com.practicum.shoppinglist.core.data.mapper.toProductEntity
import com.practicum.shoppinglist.details.domain.impl.AddProductUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteAllProductsUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteCompletedProductsUseCase
import com.practicum.shoppinglist.details.domain.impl.DeleteProductUseCase
import com.practicum.shoppinglist.details.domain.impl.GetProductListUseCase
import com.practicum.shoppinglist.details.domain.impl.UpdateProductUseCase
import com.practicum.shoppinglist.details.presentation.models.ProductDetails
import com.practicum.shoppinglist.details.presentation.state.DetailsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : ViewModel() {
    private var _state: MutableStateFlow<DetailsScreenState> =
        MutableStateFlow(DetailsScreenState())
    val state: StateFlow<DetailsScreenState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<ListAction>()
    val action: SharedFlow<ListAction> = _action

    init {
        observeProductList()
    }

    fun onIntent(intent: BaseIntent) {
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

            is BaseIntent.QueryRemoveShoppingList -> viewModelScope.launch {
                _action.emit(ListAction.RemoveItem)
            }

            is BaseIntent.RemoveListItem -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteProductUseCase(intent.id)
                }
            }

            else -> {}
        }
    }

    private fun observeProductList() {
        viewModelScope.launch(Dispatchers.IO) {
            getProductListUseCase.invoke(_state.value.shoppingListId).collect { productList ->
                _state.update {
                    it.copy(
                        productList = productList,
                        productMenuList = productList.map { it.name })
                }
            }
        }
    }
}