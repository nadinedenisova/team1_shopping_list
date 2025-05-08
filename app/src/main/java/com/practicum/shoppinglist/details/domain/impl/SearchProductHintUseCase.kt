package com.practicum.shoppinglist.details.domain.impl

import com.practicum.shoppinglist.details.domain.api.DetailsScreenRepository
import kotlinx.coroutines.flow.Flow

class SearchProductHintUseCase(
    private val repository: DetailsScreenRepository
) {
    operator fun invoke(query: String): Flow<List<String>> {
        return repository.searchProductHint(query)
    }
}