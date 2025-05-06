package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.core.domain.models.auth.Recovery
import com.practicum.shoppinglist.core.domain.models.network.ErrorType
import com.practicum.shoppinglist.core.domain.models.network.Result
import com.practicum.shoppinglist.main.domain.api.AuthorizationRepository
import kotlinx.coroutines.flow.Flow

class RecoveryUseCase(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(email: String): Flow<Result<Recovery, ErrorType>> {
        return repository.recovery(email)
    }
}