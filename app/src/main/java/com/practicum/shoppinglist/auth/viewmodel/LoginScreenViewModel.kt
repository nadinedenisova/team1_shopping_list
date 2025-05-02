package com.practicum.shoppinglist.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.shoppinglist.feature.http.data.network.AuthResponse
import com.practicum.shoppinglist.common.resources.AuthIntent
import com.practicum.shoppinglist.common.resources.AuthState
import com.practicum.shoppinglist.common.resources.AuthState.Companion.default
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.core.domain.models.network.ErrorType
import com.practicum.shoppinglist.core.domain.models.network.Result
import com.practicum.shoppinglist.main.domain.impl.LoginUseCase
import com.practicum.shoppinglist.main.domain.impl.RegistrationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private companion object {
        const val TAG = "LoginScreenViewModel"
    }

    private val _loginStateFlow = MutableStateFlow(default())
    val loginStateFlow: StateFlow<AuthState> = _loginStateFlow.asStateFlow()

    init {
        resetMode()
    }

    fun resetMode() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _loginStateFlow.update {
                    AuthState(AuthState.Status.DEFAULT)
                }
            }
        }
    }

    private fun handleLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _loginStateFlow.update {
                    AuthState(AuthState.Status.IN_PROGRESS)
                }
                loginUseCase(email, password).collect { response ->
                    if (response is Result.Success) {
                        _loginStateFlow.update {
                            AuthState(AuthState.Status.LOGIN)
                        }
                    } else {
                        _loginStateFlow.update {
                            AuthState(AuthState.Status.ERROR)
                        }
                    }
                }
            }.onFailure { error ->
                Log.e(TAG, "error in login -> $error")
                _loginStateFlow.emit(AuthState(AuthState.Status.ERROR))
            }
        }
    }

    fun processIntent(intent: BaseIntent) {
        when (intent) {
            is AuthIntent.Login -> handleLogin(email = intent.email, password = intent.password)
            else -> {}
        }
    }

}