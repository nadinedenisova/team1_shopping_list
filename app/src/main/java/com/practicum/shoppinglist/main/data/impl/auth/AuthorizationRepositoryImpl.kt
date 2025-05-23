package com.practicum.shoppinglist.main.data.impl.auth

import com.android.shoppinglist.feature.http.data.network.AuthResponse
import com.android.shoppinglist.feature.http.data.network.mapToDomain
import com.practicum.shoppinglist.core.data.network.HttpNetworkClient
import com.practicum.shoppinglist.core.data.network.model.HttpMethodType
import com.practicum.shoppinglist.core.data.network.model.mapToErrorType
import com.practicum.shoppinglist.core.domain.models.auth.Login
import com.practicum.shoppinglist.core.domain.models.auth.Refresh
import com.practicum.shoppinglist.core.domain.models.auth.Recovery
import com.practicum.shoppinglist.core.domain.models.auth.Registration
import com.practicum.shoppinglist.core.domain.models.auth.Validation
import com.practicum.shoppinglist.core.domain.models.network.ErrorType
import com.practicum.shoppinglist.core.domain.models.network.Result
import com.practicum.shoppinglist.main.data.impl.auth.dto.AuthRequest
import com.practicum.shoppinglist.main.domain.api.AuthorizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val httpNetworkClient: HttpNetworkClient<AuthRequest, AuthResponse>
) : AuthorizationRepository {

    override suspend fun registration(email: String, password: String): Flow<Result<Registration, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(
            HttpMethodType.POST,
            AuthRequest.Registration(email, password)
        )
        when (val body = response.body) {
            is AuthResponse.Registration -> {
                emit(Result.Success(body.mapToDomain()))
            }
            else -> {
                emit(Result.Failure(response.resultCode.mapToErrorType()))
            }
        }
    }

    override suspend fun login(email: String, password: String): Flow<Result<Login, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(
            HttpMethodType.POST,
            AuthRequest.Login(email, password)
        )
        when (val body = response.body) {
            is AuthResponse.Login -> {
                emit(Result.Success(body.mapToDomain()))
            }
            else -> {
                emit(Result.Failure(response.resultCode.mapToErrorType()))
            }
        }
    }

    override suspend fun recovery(email: String): Flow<Result<Recovery, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(
            HttpMethodType.POST,
            AuthRequest.Recovery(email)
        )
        when (val body = response.body) {
            is AuthResponse.Recovery -> {
                emit(Result.Success(body.mapToDomain()))
            }
            else -> {
                emit(Result.Failure(response.resultCode.mapToErrorType()))
            }
        }
    }

    override suspend fun validation(token: String): Flow<Result<Validation, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(
            HttpMethodType.GET,
            AuthRequest.Validation(token)
        )
        when (val body = response.body) {
            is AuthResponse.Validation -> {
                emit(Result.Success(body.mapToDomain()))
            }

            else -> {
                emit(Result.Failure(response.resultCode.mapToErrorType()))
            }
        }
    }

    override suspend fun refresh(token: String, refreshToken: String): Flow<Result<Refresh, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(
            HttpMethodType.POST,
            AuthRequest.RefreshToken(refreshToken, token)
        )
        when (val body = response.body) {
            is AuthResponse.Refresh -> {
                emit(Result.Success(body.mapToDomain()))
            }

            else -> {
                emit(Result.Failure(response.resultCode.mapToErrorType()))
            }
        }
    }

}