package com.practicum.shoppinglist.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.practicum.shoppinglist.App
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.SearchShoppingListState
import com.practicum.shoppinglist.common.resources.ShoppingListState
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import com.practicum.shoppinglist.di.api.daggerViewModel
import com.practicum.shoppinglist.main.ui.recycler.ItemList
import com.practicum.shoppinglist.main.ui.recycler.ItemListSearch
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier,
    isSearchActive: MutableState<Boolean>,
    showAddShoppingListDialog: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val factory = remember {
        (context.applicationContext as App).appComponent.viewModelFactory()
    }
    val viewModel = daggerViewModel<MainScreenViewModel>(factory)
    val contentState by viewModel.shoppingListStateFlow.collectAsStateWithLifecycle()
    val searchState by viewModel.searchShoppingListStateFlow.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var selectedList by remember { mutableStateOf<ListItem?>(null) }
    val searchQuery = remember { mutableStateOf("") }

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

    Box(
        Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Search(
                visible = isSearchActive.value,
                searchQuery = searchQuery,
                onBackClick = {
                    isSearchActive.value = false
                    searchQuery.value = ""
                    viewModel.clearSearchResults()
                },
                onValueChange = { newValue ->
                    searchQuery.value = newValue
                    viewModel.search(searchQuery.value)
                },
                onBtnClearClick = {
                    searchQuery.value = ""
                    viewModel.clearSearchResults()
                },
            )
            SearchShoppingList(
                visible = isSearchActive.value
                        && searchQuery.value.isNotEmpty()
                        && searchState is SearchShoppingListState.SearchResults,
                state = searchState,
                onItemClick = {
                    //TO-DO
                }
            )
            NoData(
                visible = isSearchActive.value
                        && searchQuery.value.isNotEmpty()
                        && searchState is SearchShoppingListState.NothingFound,
                modifier = Modifier.padding(top = 64.dp).fillMaxSize(),
                image = SLTheme.images.nothingFound,
                title = stringResource(R.string.nothing_found_title),
                message = stringResource(R.string.no_shopping_lists_message),
            )

            Box {
                ShoppingList(
                    visible = contentState is ShoppingListState.ShoppingList,
                    state = contentState,
                    onItemClick = {
                        //TO-DO
                    },
                    onIconClick = { id ->
                        selectedList = id
                        showBottomSheet = true
                    },
                    onRemove = { id ->
                        viewModel.removeShoppingList(id) }
                )
                NoData(
                    visible = contentState is ShoppingListState.NoShoppingLists,
                    image = SLTheme.images.noShoppingList,
                    title = stringResource(R.string.no_shopping_lists_title),
                    message = stringResource(R.string.no_shopping_lists_message),
                )
                AddShoppingListDialog(
                    visible = showAddShoppingListDialog.value,
                    onDismiss = { showAddShoppingListDialog.value = false },
                    onConfirm = { name ->
                        viewModel.addShoppingList(name = name, icon = R.drawable.ic_list.toLong())
                    }
                )
                Scrim(
                    visible = isSearchActive.value,
                    isSearchActive = isSearchActive
                )
            }
        }
    }
}

@Composable
fun Scrim(
    visible: Boolean,
    isSearchActive: MutableState<Boolean>,
) {
    if (!visible) return

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = .32f))
            .clickable {
                isSearchActive.value = false
            }
    )
}

@Composable
fun ShoppingList(
    visible: Boolean,
    state: ShoppingListState,
    onItemClick: () -> Unit,
    onIconClick: (ListItem) -> Unit = {},
    onRename: () -> Unit = {},
    onCopy: () -> Unit = {},
    onRemove: (Long) -> Unit,
) {
    if (!visible) return

    val items = when(state) {
        is ShoppingListState.ShoppingList -> state.list
        else -> null
    }

    if (items == null) return

    var openItemId by remember { mutableStateOf<Long?>(null) }

    LazyColumn(
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_8x))
            .fillMaxSize(),
    ) {
        items(items, key = { it.id }) { item ->
            ItemList(
                list = item,
                itemId = item.id,
                openItemId = openItemId,
                onItemClick = {
                    onItemClick()
                    openItemId = null
                },
                onIconClick = { onIconClick(item) },
                onItemOpened = { id -> openItemId = id },
                onItemClosed = { if (openItemId == item.id) openItemId = null },
                onRename = onRename,
                onCopy = onCopy,
                onRemove = { onRemove(item.id) },
            )
        }
    }
}

@Composable
fun SearchShoppingList(
    visible: Boolean,
    state: SearchShoppingListState,
    onItemClick: () -> Unit,
) {
    if (!visible) return

    val items = when(state) {
        is SearchShoppingListState.SearchResults -> state.list
        else -> null
    }

    if (items == null) return

    LazyColumn(
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.padding_4x))
            .fillMaxSize(),
    ) {
        items(items) { item ->
            ItemListSearch(
                list = item,
                onItemClick = { onItemClick() },
            )
        }
    }
}

@Composable
fun NoData(
    visible: Boolean,
    modifier: Modifier = Modifier,
    image: Int,
    title: String,
    message: String,
) {
    if (!visible) return

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            alignment = Alignment.Center,
            painter = painterResource(id = image),
            contentDescription = null,
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_image)),
        )
        Text(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_4x)),
        )
    }
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