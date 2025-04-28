package com.practicum.shoppinglist.details.domain.impl

import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository

class DeleteCompletedProductsUseCase(
    private val repository: DetailsScreenRepository
) {
    suspend operator fun invoke(shoppingListId: Long): Long {
        return repository.removeCompletedProducts(shoppingListId)
    }
}