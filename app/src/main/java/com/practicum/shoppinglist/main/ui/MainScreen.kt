package com.practicum.shoppinglist.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.practicum.shoppinglist.App
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.ShoppingListState
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.di.api.daggerViewModel
import com.practicum.shoppinglist.main.ui.recycler.ItemList
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier,
    showAddShoppingListDialog: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val factory = remember {
        (context.applicationContext as App).appComponent.viewModelFactory()
    }
    val viewModel = daggerViewModel<MainScreenViewModel>(factory)
    val state by viewModel.shoppingListStateFlow.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var selectedList by remember { mutableStateOf<ListItem?>(null) }

    IconsBottomSheet(
        visible = showBottomSheet,
        bottomSheetState = bottomSheetState,
        onDismissRequest = { showBottomSheet = false },
        hideBottomSheet = {
            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    showBottomSheet = false
                }
            }
        },
        onIconClick = { icon ->
            viewModel.updateShoppingList(selectedList!!.copy(iconResId = icon))
        },
    )
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ShoppingList(
            visible = state is ShoppingListState.ShoppingList,
            state = state,
            onItemClick = {
                //TO-DO
            },
            onIconClick = { id ->
                selectedList = id
                showBottomSheet = true
            }
        )
        NoShoppingLists(visible = state is ShoppingListState.NoShoppingLists)
        AddShoppingListDialog(
            visible = showAddShoppingListDialog.value,
            onDismiss = { showAddShoppingListDialog.value = false },
            onConfirm = { name ->
                viewModel.addShoppingList(name = name, icon = R.drawable.ic_list.toLong())
            }
        )
    }
}

@Composable
fun ShoppingList(
    visible: Boolean,
    state: ShoppingListState,
    onItemClick: () -> Unit,
    onIconClick: (ListItem) -> Unit,
) {
    if (!visible) return

    val items = if (state is ShoppingListState.ShoppingList) state.list else null

    if (items == null) return

    LazyColumn(
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_8x))
            .fillMaxSize(),
    ) {
        items(items) { item ->
            ItemList(
                list = item,
                onItemClick = { onItemClick() },
                onIconClick = { onIconClick(item) },
            )
        }
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

    var shoppingListName by remember { mutableStateOf("") }
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
                value = shoppingListName,
                onValueChange = { shoppingListName = it },
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
                onConfirm(shoppingListName)
                onDismiss()
            }) {
                Text(stringResource(R.string.create))
            }
        },
    )
}