package com.practicum.shoppinglist.main.domain.impl

import com.practicum.shoppinglist.main.domain.api.TokenStorage
import jakarta.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    operator fun invoke(): Boolean {
        return tokenStorage.getToken()?.isNotEmpty() == true
    }
}