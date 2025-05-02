package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.core.domain.models.network.Result
import com.practicum.shoppinglist.main.domain.api.AuthorizationRepository
import com.practicum.shoppinglist.main.domain.api.TokenStorage
import jakarta.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class TokenValidationUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authRepository: AuthorizationRepository
) {
    suspend operator fun invoke(): Boolean {
        val currentToken = tokenStorage.getToken() ?: return false

        val validationResult = authRepository.validation(currentToken).firstOrNull()
        if (validationResult is Result.Success && validationResult.data.isValid) {
            return true
        }

        val refreshToken = tokenStorage.getRefreshToken() ?: return false
        val refreshResult = authRepository.refresh(currentToken, refreshToken).firstOrNull()

        return if (refreshResult is Result.Success && refreshResult.data.refreshToken?.isNotEmpty() == true) {
            tokenStorage.saveToken(refreshResult.data.token)
            tokenStorage.saveRefreshToken(refreshResult.data.refreshToken)
            true
        } else {
            false
        }
    }
}