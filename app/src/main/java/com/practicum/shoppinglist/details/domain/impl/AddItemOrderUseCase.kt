package com.practicum.shoppinglist.details.domain.impl

import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository

class AddItemOrderUseCase(
    private val repository: DetailsScreenRepository
) {
    suspend operator fun invoke(shoppingListId: Long, itemOrder: Map<Long, Long>): Long {
        return repository.addItemSortOrder(shoppingListId, itemOrder)
    }
}