package com.practicum.shoppinglist.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.shoppinglist.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.practicum.shoppinglist.App
import com.practicum.shoppinglist.di.api.daggerViewModel
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel

@Composable
fun MainScreen(
    modifier: Modifier,
    showAddShoppingListDialog: MutableState<Boolean>,
) {
    val context = LocalContext.current
    /*val factory = remember {
        (context as App).appComponent.viewModelFactory()
    }
    val viewModel = daggerViewModel<MainScreenViewModel>(factory)*/

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NoShoppingLists(visible = true)
        AddShoppingListDialog(
            visible = showAddShoppingListDialog.value,
            onDismiss = { showAddShoppingListDialog.value = false },
            onConfirm = {

            }
        )
    }
}

@Composable
fun NoShoppingLists(
    visible: Boolean,
) {
    if (!visible) return

    Image(
        alignment = Alignment.Center,
        painter = painterResource(id = R.drawable.no_shopping_lists),
        contentDescription = null,
    )
    Text(
        text = stringResource(R.string.no_shopping_lists_title),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_image)),
    )
    Text(
        text = stringResource(R.string.no_shopping_lists_message),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_4x)),
    )
}

@Composable
fun AddShoppingListDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    if (!visible) return

    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_shopping_list),
                contentDescription = null
            )
        },
        title = { Text(stringResource(R.string.add_shopping_list)) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(R.string.shopping_list_name)) },
                placeholder = { Text(stringResource(R.string.new_shopping_list)) },
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.background,
                ),
                modifier = Modifier.focusRequester(focusRequester),
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(text)
                onDismiss()
            }) {
                Text(stringResource(R.string.create))
            }
        },
    )
}