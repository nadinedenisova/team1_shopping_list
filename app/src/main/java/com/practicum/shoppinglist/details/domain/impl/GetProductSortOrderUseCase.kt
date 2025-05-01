package com.practicum.shoppinglist.details.domain.impl

import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class GetProductSortOrderUseCase(
    private val repository: DetailsScreenRepository
) {
    operator fun invoke(shoppingListId: Long): Flow<Map<Long, Long>> {
        return repository.getSortOrderByShoppingListId(shoppingListId)
            .transform {
                emit(it.associate { it.product_id to it.order_index })
            }
    }
}