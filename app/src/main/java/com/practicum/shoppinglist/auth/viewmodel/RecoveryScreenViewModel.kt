package com.practicum.shoppinglist.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.shoppinglist.BuildConfig
import com.practicum.shoppinglist.common.resources.AuthIntent
import com.practicum.shoppinglist.common.resources.AuthState
import com.practicum.shoppinglist.common.resources.AuthState.Companion.default
import com.practicum.shoppinglist.core.domain.models.network.Result
import com.practicum.shoppinglist.main.domain.impl.RecoveryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecoveryScreenViewModel @Inject constructor(
    private val recoveryUseCase: RecoveryUseCase,
) : ViewModel() {

    private val _recoveryStateFlow = MutableStateFlow(default())
    val recoveryStateFlow: StateFlow<AuthState> = _recoveryStateFlow.asStateFlow()

    fun reset() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _recoveryStateFlow.update {
                    AuthState(AuthState.Status.DEFAULT)
                }
            }
        }
    }

    private fun handleRecovery(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _recoveryStateFlow.update {
                    AuthState(AuthState.Status.IN_PROGRESS)
                }
                recoveryUseCase(email).collect { response ->
                    if (response is Result.Success) {
                        _recoveryStateFlow.update {
                            AuthState(AuthState.Status.RECOVERED)
                        }
                    } else {
                        _recoveryStateFlow.update {
                            AuthState(AuthState.Status.ERROR)
                        }
                    }
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "error in login -> $error")
                }
                _recoveryStateFlow.emit(AuthState(AuthState.Status.ERROR))
            }
        }
    }

    fun handleRecovery(intent: AuthIntent.Recovery) {
        handleRecovery(email = intent.email)
    }

    private companion object {
        const val TAG = "RecoveryScreenViewModel"
    }

}