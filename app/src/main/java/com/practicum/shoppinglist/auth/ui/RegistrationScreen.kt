package com.practicum.shoppinglist.auth.ui

import android.util.Patterns
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.auth.viewmodel.RegistrationScreenViewModel
import com.practicum.shoppinglist.core.presentation.ui.components.SLOutlineTextField

@Composable
fun RegistrationScreen(
    navController: NavController,
    registrationViewModel: RegistrationScreenViewModel
) {
    RegistrationForm(
        onRegistrationClick = {

        }
    )
}

@Composable
fun RegistrationForm(
    onRegistrationClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isConfirmPasswordError by remember { mutableStateOf(false) }

    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= 6
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
                isPasswordError = it.length < 6 && it.isNotEmpty()
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
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onRegistrationClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled
        ) {
            Text(stringResource(R.string.registration))
        }
    }
}