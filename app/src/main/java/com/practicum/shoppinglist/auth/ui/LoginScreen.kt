package com.practicum.shoppinglist.auth.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.auth.viewmodel.LoginScreenViewModel
import com.practicum.shoppinglist.common.resources.AuthIntent
import com.practicum.shoppinglist.common.resources.AuthState
import com.practicum.shoppinglist.common.utils.Constants.PASSWORD_LENGTH
import com.practicum.shoppinglist.core.presentation.ui.components.PasswordTextField
import com.practicum.shoppinglist.core.presentation.ui.components.SLOutlineTextField
import com.practicum.shoppinglist.main.ui.Routes

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginScreenViewModel,
) {

    val state by loginViewModel.loginStateFlow.collectAsStateWithLifecycle()

    if (state.status == AuthState.Status.LOGIN) {
        loginViewModel.resetMode()
        navController.navigate(Routes.MainScreen.name)
    }
    if (state.status == AuthState.Status.ERROR) {
        Toast.makeText(LocalContext.current, stringResource(R.string.invalid_login), Toast.LENGTH_SHORT).show()
    }

    LoginForm(
        navController = navController,
        onLoginClick = {email, password ->
            loginViewModel.processIntent(AuthIntent.Login(email, password))
        }
    )
}

@Composable
fun LoginForm(
    navController: NavController,
    onLoginClick: (email: String, password: String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.isNotEmpty()

    val isButtonEnabled = isEmailValid && isPasswordValid

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
        Button(
            onClick = {
                onLoginClick(email, password)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled
        ) {
            Text(stringResource(R.string.enter))
        }
        ClickableTextButton(stringResource(R.string.registration), onClick = {
            navController.navigate(Routes.Registration.name)
        })
        ClickableTextButton(stringResource(R.string.restore_password), onClick = {
            navController.navigate(Routes.RestorePassword.name)
        })
    }
}

@Composable
fun ClickableTextButton(text: String, onClick: () -> Unit = {},) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextButton(
            onClick = onClick,
        ) {
            Text(text = text, color = Color.DarkGray)
        }
    }
}
