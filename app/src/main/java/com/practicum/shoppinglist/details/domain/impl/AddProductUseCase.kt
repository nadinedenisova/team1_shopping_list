package com.practicum.shoppinglist.details.domain.impl

import com.practicum.shoppinglist.ProductEntity
import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository

class AddProductUseCase(
    private val repository: DetailsScreenRepository
) {
    suspend operator fun invoke(shoppingListId: Long, item: ProductEntity): Long {
        return repository.addProduct(shoppingListId, item)
    }
}