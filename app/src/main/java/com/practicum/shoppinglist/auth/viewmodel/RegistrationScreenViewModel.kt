package com.practicum.shoppinglist.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.practicum.shoppinglist.main.domain.impl.RegistrationUseCase
import javax.inject.Inject

class RegistrationScreenViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
) : ViewModel() {

    private companion object {
        const val TAG = "RegistrationScreenViewModel"
    }

}