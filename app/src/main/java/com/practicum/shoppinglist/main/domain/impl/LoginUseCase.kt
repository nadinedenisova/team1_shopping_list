package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.core.domain.models.auth.Login
import com.practicum.shoppinglist.core.domain.models.network.ErrorType
import com.practicum.shoppinglist.core.domain.models.network.Result
import com.practicum.shoppinglist.main.domain.api.AuthorizationRepository
import com.practicum.shoppinglist.main.domain.api.TokenStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class LoginUseCase(
    private val repository: AuthorizationRepository,
    private val tokenStorage: TokenStorage,
) {
    suspend operator fun invoke(email: String, password: String): Flow<Result<Login, ErrorType>> {
        return repository.login(email, password).onEach { result ->
            if (result is Result.Success) {
                result.let { login ->
                    tokenStorage.saveToken(login.data.accessToken)
                    tokenStorage.saveRefreshToken(login.data.refreshToken)
                }
            }
        }
    }

    fun logout() {
        tokenStorage.clearToken()
    }

    fun hasToken(): Boolean {
        return tokenStorage.getToken() != null
    }
}