package com.practicum.shoppinglist.auth.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.core.presentation.ui.components.SLOutlineTextField
import com.practicum.shoppinglist.main.ui.Routes

@Composable
fun LoginScreen(
    navController: NavController,
) {
    LoginForm(
        navController = navController,
        onLoginClick = {}
    )
}

@Composable
fun LoginForm(
    navController: NavController,
    onLoginClick: () -> Unit = {},
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
        SLOutlineTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
                isPasswordError = it.isEmpty()
            },
            label = stringResource(R.string.password),
            placeholder = stringResource(R.string.password),
            isError = isPasswordError,
            errorMessage = if (isPasswordError) stringResource(R.string.password_too_short) else null,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onLoginClick,
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
