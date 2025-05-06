package com.practicum.shoppinglist.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.auth.viewmodel.RecoveryScreenViewModel
import com.practicum.shoppinglist.common.resources.AuthIntent
import com.practicum.shoppinglist.common.resources.AuthState
import com.practicum.shoppinglist.core.presentation.navigation.MainScreen
import com.practicum.shoppinglist.core.presentation.ui.components.SLOutlineTextField

@Composable
fun RestorePasswordScreen(
    navController: NavController,
    recoveryScreenViewModel: RecoveryScreenViewModel
) {
    val state by recoveryScreenViewModel.recoveryStateFlow.collectAsStateWithLifecycle()

    if (state.status == AuthState.Status.RECOVERED) {
        recoveryScreenViewModel.reset()
        navController.navigate(MainScreen)
    }

    RestorePasswordForm(
        status = state.status,
        onRecoveryClick = {
            email -> recoveryScreenViewModel.handleRecovery(AuthIntent.Recovery(email))
        }
    )
}

@Composable
fun RestorePasswordForm(
    status: AuthState.Status,
    onRecoveryClick: (email: String) -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }

    var isEmailError by rememberSaveable { mutableStateOf(false) }

    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isButtonEnabled = isEmailValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SLOutlineTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = {
                email = it
                isEmailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() && it.isNotEmpty()
            },
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.email),
            isError = isEmailError,
            errorMessage = if (isEmailError) stringResource(R.string.invalid_email) else null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onRecoveryClick(email)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled && status != AuthState.Status.IN_PROGRESS
        ) {
            if (status == AuthState.Status.IN_PROGRESS) {
                Text(stringResource(R.string.process))
            } else {
                Text(stringResource(R.string.recovery))
            }
        }
    }
}