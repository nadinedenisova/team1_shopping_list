package com.practicum.shoppinglist.details.domain.impl

import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository
import kotlinx.coroutines.flow.Flow

class GetProductListUseCase(
    private val repository: DetailsScreenRepository
) {
    suspend operator fun invoke(shoppingListId: Long): Flow<List<ProductItem>> {
        return repository.getAllProduct(shoppingListId)
    }
}