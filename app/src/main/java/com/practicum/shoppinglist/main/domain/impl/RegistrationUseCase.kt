package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.core.domain.models.auth.Registration
import com.practicum.shoppinglist.core.domain.models.network.ErrorType
import com.practicum.shoppinglist.core.domain.models.network.Result
import com.practicum.shoppinglist.main.domain.api.AuthorizationRepository
import kotlinx.coroutines.flow.Flow

class RegistrationUseCase(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Result<Registration, ErrorType>> {
        return repository.registration(email, password)
    }
}