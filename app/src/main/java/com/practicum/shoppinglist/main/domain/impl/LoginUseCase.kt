package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.core.domain.models.auth.Login
import com.practicum.shoppinglist.core.domain.models.network.ErrorType
import com.practicum.shoppinglist.core.domain.models.network.Result
import com.practicum.shoppinglist.main.domain.api.AuthorizationRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Result<Login, ErrorType>> {
        return repository.login(email, password)
    }
}