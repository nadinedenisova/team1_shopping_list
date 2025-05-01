package com.practicum.shoppinglist.details.domain.impl

import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository

class DeleteProductUseCase(
    private val repository: DetailsScreenRepository
) {
    suspend operator fun invoke(productId: Long): Long {
        return repository.removeProduct(productId)
    }
}