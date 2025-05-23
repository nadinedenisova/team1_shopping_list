package com.practicum.shoppinglist.main.domain.api

import com.practicum.shoppinglist.core.domain.models.auth.Login
import com.practicum.shoppinglist.core.domain.models.auth.Refresh
import com.practicum.shoppinglist.core.domain.models.auth.Registration
import com.practicum.shoppinglist.core.domain.models.auth.Validation
import com.practicum.shoppinglist.core.domain.models.auth.Recovery
import com.practicum.shoppinglist.core.domain.models.network.ErrorType
import com.practicum.shoppinglist.core.domain.models.network.Result
import kotlinx.coroutines.flow.Flow

interface AuthorizationRepository {
    suspend fun registration(email: String, password: String): Flow<Result<Registration, ErrorType>>
    suspend fun login(email: String, password: String): Flow<Result<Login, ErrorType>>
    suspend fun recovery(email: String): Flow<Result<Recovery, ErrorType>>
    suspend fun validation(token: String): Flow<Result<Validation, ErrorType>>
    suspend fun refresh(currentToken: String, refreshToken: String): Flow<Result<Refresh, ErrorType>>
}