package com.practicum.shoppinglist.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.shoppinglist.common.resources.AuthIntent
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.main.domain.impl.RegistrationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationScreenViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
) : ViewModel() {

    private companion object {
        const val TAG = "RegistrationScreenViewModel"
    }

    private fun handleRegistration(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                registrationUseCase(email, password)
            }.onFailure { error ->
                Log.e(TAG, "error in registration -> $error")
            }
        }
    }

    fun processIntent(intent: BaseIntent) {
        when (intent) {
            is AuthIntent.Registration -> handleRegistration(email = intent.email, password = intent.password)
            else -> {}
        }
    }

}