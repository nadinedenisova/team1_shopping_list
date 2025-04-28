package com.practicum.shoppinglist.details.domain.impl

import com.practicum.shoppinglist.ProductEntity
import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository

class UpdateProductUseCase(
    private val repository: DetailsScreenRepository
) {
    suspend operator fun invoke(new: ProductEntity): ProductItem {
        return repository.updateProduct(new)
    }
}