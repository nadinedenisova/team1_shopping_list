package com.practicum.shoppinglist.auth.ui

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.auth.viewmodel.RegistrationScreenViewModel
import com.practicum.shoppinglist.common.resources.AuthIntent
import com.practicum.shoppinglist.common.resources.AuthState
import com.practicum.shoppinglist.common.utils.Constants.PASSWORD_LENGTH
import com.practicum.shoppinglist.core.presentation.ui.components.PasswordTextField
import com.practicum.shoppinglist.core.presentation.ui.components.SLOutlineTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavController,
    callback: () -> Unit,
    registrationViewModel: RegistrationScreenViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val state by registrationViewModel.registrationStateFlow.collectAsStateWithLifecycle()

    val errorMessage = stringResource(R.string.invalid_registration)

    LaunchedEffect(state.status) {
        when (state.status) {
            AuthState.Status.REGISTERED -> {
                registrationViewModel.resetMode()
                callback()
            }
            AuthState.Status.ERROR -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = errorMessage
                    )
                }
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.registration))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        RegistrationForm(
            modifier = Modifier.padding(innerPadding),
            state.status,
            onRegistrationClick = { email, password ->
                keyboardController?.hide()
                registrationViewModel.processRegistration(
                    AuthIntent.Registration(email, password)
                )
            }
        )
    }
}

@Composable
fun RegistrationForm(
    modifier: Modifier = Modifier,
    status: AuthState.Status,
    onRegistrationClick: (email: String, password: String) -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordError by rememberSaveable { mutableStateOf(false) }

    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= PASSWORD_LENGTH
    val isPasswordsMatch = password == confirmPassword

    val isButtonEnabled = isEmailValid && isPasswordValid && isPasswordsMatch

    Column(
        modifier = modifier
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

        PasswordTextField(
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

        PasswordTextField(
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