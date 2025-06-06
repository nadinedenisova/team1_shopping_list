package com.practicum.shoppinglist.core.presentation.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SLOutlineTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        modifier = modifier
//            .defaultMinSize(
//                minWidth = OutlinedTextFieldDefaults.MinWidth,
//                minHeight = OutlinedTextFieldDefaults.MinHeight
//            )
        ,
        value = value,
        singleLine = true,
        maxLines = 1,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(SLTheme.slColorScheme.materialScheme.primary),
        textStyle = SLTheme.typography.bodyLarge
            .copy(
                color = SLTheme.slColorScheme.materialScheme.onSurface
            ),
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        decorationBox = @Composable { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                placeholder = @Composable {
                    if (placeholder.isNullOrEmpty()) null
                    else TFPlaceholder(placeholder)
                },
                label = @Composable {
                    if (label.isNullOrEmpty()) null
                    else TFLabel(label)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedLabelColor = SLTheme.slColorScheme.materialScheme.onSurfaceVariant,
                    focusedLabelColor = SLTheme.slColorScheme.materialScheme.secondary,
                    unfocusedPlaceholderColor = SLTheme.slColorScheme.materialScheme.onSurfaceVariant,
                    focusedPlaceholderColor = SLTheme.slColorScheme.materialScheme.onSurfaceVariant,
                ),
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                trailingIcon = trailingIcon,
                container = {
                    OutlinedTextFieldDefaults.Container(
                        modifier = Modifier.width(IntrinsicSize.Min),
                        enabled = true,
                        isError = isError,
                        interactionSource = interactionSource,
                        focusedBorderThickness = 3.dp,
                        unfocusedBorderThickness = 1.dp,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = SLTheme.slColorScheme.materialScheme.onSurfaceVariant,
                            focusedBorderColor = SLTheme.slColorScheme.materialScheme.secondary,
                        )
                    )
                }
            )
        }
    )
    if (isError && errorMessage != null) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

@Composable
private fun TFLabel(text: String) {
    Surface {
        Text(
            modifier = Modifier,
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun TFPlaceholder(text: String) {
    Text(
        text = text,
        style = SLTheme.typography.bodyLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}


@Preview(name = "Светлая тема", showSystemUi = true, device = PIXEL_6)
@Composable
fun SLOutlineTextFieldPreviewLight() {
    SLTheme(darkTheme = false) {
        Scaffold(
            containerColor = SLTheme.slColorScheme.materialScheme.surfaceContainerHigh
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                var text by remember { mutableStateOf("") }

                SLOutlineTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = stringResource(R.string.shopping_list_name),
                    placeholder = stringResource(R.string.new_shopping_list),
                )

                Spacer(Modifier.height(32.dp))

                SLOutlineTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = stringResource(R.string.shopping_list_name),
                    placeholder = stringResource(R.string.new_shopping_list),
                )
            }
        }
    }
}

@Preview(name = "Темная тема", showSystemUi = true, device = PIXEL_6)
@Composable
fun SLOutlineTextFieldPreviewDark() {
    SLTheme(darkTheme = true) {
        Scaffold(
            containerColor = SLTheme.slColorScheme.materialScheme.surfaceContainerHigh
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                var text by remember { mutableStateOf("") }

                SLOutlineTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = stringResource(R.string.shopping_list_name),
                    placeholder = stringResource(R.string.new_shopping_list),
                )

                Spacer(Modifier.height(32.dp))

                SLOutlineTextField(
                    value = "Продукты",
                    onValueChange = { text = it },
                    label = stringResource(R.string.shopping_list_name),
                    placeholder = stringResource(R.string.new_shopping_list),
                )
            }
        }
    }
}


@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { if (!label.isNullOrEmpty()) Text(label) },
        placeholder = { if (!placeholder.isNullOrEmpty()) Text(placeholder) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedLabelColor = SLTheme.slColorScheme.materialScheme.onSurfaceVariant,
            focusedLabelColor = SLTheme.slColorScheme.materialScheme.secondary,
            unfocusedPlaceholderColor = SLTheme.slColorScheme.materialScheme.onSurfaceVariant,
            focusedPlaceholderColor = SLTheme.slColorScheme.materialScheme.onSurfaceVariant,
            unfocusedBorderColor = SLTheme.slColorScheme.materialScheme.onSurfaceVariant,
            focusedBorderColor = SLTheme.slColorScheme.materialScheme.secondary,
        ),
        isError = isError,
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Person else Icons.Filled.Lock,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                )
            }
        },
    )
    if (isError && errorMessage != null) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}