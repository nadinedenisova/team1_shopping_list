package com.practicum.shoppinglist.auth.ui

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.auth.viewmodel.RegistrationScreenViewModel
import com.practicum.shoppinglist.common.resources.AuthIntent
import com.practicum.shoppinglist.common.resources.AuthState
import com.practicum.shoppinglist.common.utils.Constants.PASSWORD_LENGTH
import com.practicum.shoppinglist.core.presentation.navigation.MainScreen
import com.practicum.shoppinglist.core.presentation.ui.components.SLOutlineTextField

@Composable
fun RegistrationScreen(
    navController: NavController,
    registrationViewModel: RegistrationScreenViewModel
) {

    val state by registrationViewModel.registrationStateFlow.collectAsStateWithLifecycle()

    if (state.status == AuthState.Status.REGISTERED) {
        registrationViewModel.resetMode()
        navController.navigate(MainScreen)
    }
    if (state.status == AuthState.Status.ERROR) {
        Toast.makeText(LocalContext.current, stringResource(R.string.invalid_registration), Toast.LENGTH_SHORT).show()
    }

    RegistrationForm(
        state.status,
        onRegistrationClick = {email, password ->
            registrationViewModel.processIntent(AuthIntent.Registration(email, password))
        }
    )
}

@Composable
fun RegistrationForm(
    status: AuthState.Status,
    onRegistrationClick: (email: String, password: String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isConfirmPasswordError by remember { mutableStateOf(false) }

    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= PASSWORD_LENGTH
    val isPasswordsMatch = password == confirmPassword

    val isButtonEnabled = isEmailValid && isPasswordValid && isPasswordsMatch

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SLOutlineTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = {
                email = it
                isEmailError = !Patterns.EMAIL_ADDRESS.matcher(it).matches() && it.isNotEmpty()
            },
            label = stringResource(R.string.email),
            placeholder = stringResource(R.string.email),
            isError = isEmailError,
            errorMessage = if (isEmailError) stringResource(R.string.invalid_email) else null
        )
        Spacer(modifier = Modifier.height(16.dp))

        SLOutlineTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
                isPasswordError = it.length < PASSWORD_LENGTH && it.isNotEmpty()
            },
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            isError = isPasswordError,
            errorMessage = if (isPasswordError) stringResource(R.string.password_too_short) else null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        SLOutlineTextField(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                isConfirmPasswordError = it != password && it.isNotEmpty()
            },
            label = stringResource(R.string.confirm_password),
            placeholder = stringResource(R.string.confirm_password),
            isError = isConfirmPasswordError,
            errorMessage = if (isConfirmPasswordError) stringResource(R.string.passwords_do_not_match) else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onRegistrationClick(email, password)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled && status != AuthState.Status.IN_PROGRESS,
        ) {
            if (status == AuthState.Status.IN_PROGRESS) {
                Text(stringResource(R.string.process))
            } else {
                Text(stringResource(R.string.registration))
            }
        }
    }
}