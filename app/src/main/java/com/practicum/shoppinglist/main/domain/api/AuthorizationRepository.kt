package com.practicum.shoppinglist.main.domain.api

import com.android.shoppinglist.feature.http.data.network.AuthResponse
import com.practicum.shoppinglist.core.domain.models.auth.Login
import com.practicum.shoppinglist.core.domain.models.auth.Registration
import com.practicum.shoppinglist.core.domain.models.auth.Validation
import com.practicum.shoppinglist.core.domain.models.network.ErrorType
import com.practicum.shoppinglist.core.domain.models.network.Result
import kotlinx.coroutines.flow.Flow

interface AuthorizationRepository {
    fun registration(): Flow<Result<Registration, ErrorType>>
    fun login(): Flow<Result<Login, ErrorType>>
    fun validation(): Flow<Result<Validation, ErrorType>>
    fun refresh(): Flow<Result<AuthResponse.Refresh, ErrorType>>
}