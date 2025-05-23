package com.practicum.shoppinglist.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.shoppinglist.common.resources.AuthIntent
import com.practicum.shoppinglist.common.resources.AuthState
import com.practicum.shoppinglist.common.resources.AuthState.Companion.default
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.core.domain.models.network.Result
import com.practicum.shoppinglist.main.domain.impl.RegistrationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationScreenViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
) : ViewModel() {

    private val _registrationStateFlow = MutableStateFlow(default())
    val registrationStateFlow: StateFlow<AuthState> = _registrationStateFlow.asStateFlow()

    init {
        resetMode()
    }

    fun resetMode() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _registrationStateFlow.update {
                    AuthState(AuthState.Status.DEFAULT)
                }
            }
        }
    }

    private fun handleRegistration(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _registrationStateFlow.update {
                    AuthState(AuthState.Status.IN_PROGRESS)
                }
                registrationUseCase(email, password).collect { response ->
                    if (response is Result.Success) {
                        _registrationStateFlow.update {
                            AuthState(AuthState.Status.REGISTERED)
                        }
                    } else {
                        _registrationStateFlow.update {
                            AuthState(AuthState.Status.ERROR)
                        }
                    }
                }
            }.onFailure { error ->
                Log.e(TAG, "error in registration -> $error")
                _registrationStateFlow.emit(AuthState(AuthState.Status.ERROR))
            }
        }
    }

    fun processRegistration(intent: AuthIntent.Registration) {
        handleRegistration(email = intent.email, password = intent.password)
    }

    private companion object {
        const val TAG = "RegistrationScreenViewModel"
    }

}