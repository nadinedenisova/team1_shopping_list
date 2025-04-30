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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.common.resources.ShoppingListIntent
import com.practicum.shoppinglist.common.resources.ShoppingListState
import com.practicum.shoppinglist.common.utils.itemSaver
import com.practicum.shoppinglist.core.domain.models.BaseItem
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import com.practicum.shoppinglist.main.ui.recycler.ItemList
import com.practicum.shoppinglist.main.ui.recycler.ItemListSearch
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel,
    isSearchActive: MutableState<Boolean>,
    showAddShoppingListDialog: MutableState<Boolean>,
    showRemoveAllShoppingListsDialog: MutableState<Boolean>,
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.shoppingListStateFlow.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var selectedList by rememberSaveable(stateSaver = itemSaver(ListItem.serializer())) { mutableStateOf(null) }
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val showEditShoppingListDialog = rememberSaveable { mutableStateOf(false) }
    val showRemoveShoppingListDialog = rememberSaveable { mutableStateOf(false) }

    if (showBottomSheet) {
        IconsBottomSheet(
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
                selectedList?.let {
                    /*selectedList =*/ viewModel.processIntent(ShoppingListIntent.UpdateShoppingList(list = it.copy(iconResId = icon)))
                }
            },
        )
    }

    Box(
        Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Search(
                visible = isSearchActive.value,
                searchQuery = searchQuery,
                onBackClick = {
                    isSearchActive.value = false
                    searchQuery.value = ""
                    viewModel.processIntent(ShoppingListIntent.ClearSearchResults)
                },
                onValueChange = { newValue ->
                    searchQuery.value = newValue
                    viewModel.processIntent(ShoppingListIntent.Search(searchQuery = searchQuery.value))
                },
                onBtnClearClick = {
                    searchQuery.value = ""
                    viewModel.processIntent(ShoppingListIntent.ClearSearchResults)
                },
            )
            SearchShoppingList(
                visible = isSearchActive.value
                        && searchQuery.value.isNotEmpty()
                        && state.status == ShoppingListState.Status.SEARCH_RESULTS,
                state = state,
                onItemClick = {
                    //TO-DO
                }
            )
            NoData(
                visible = isSearchActive.value
                        && searchQuery.value.isNotEmpty()
                        && state.status == ShoppingListState.Status.NOTHING_FOUND,
                modifier = Modifier.padding(top = 64.dp).fillMaxSize(),
                image = R.drawable.nothing_found_light,
                title = stringResource(R.string.nothing_found_title),
                message = stringResource(R.string.nothing_found_message),
            )

            Box {
                ShoppingList(
                    visible = state.status == ShoppingListState.Status.CONTENT,
                    viewModel = viewModel,
                    state = state,
                    onItemClick = { list ->
                        selectedList = list
                        navController.navigate("${Routes.ProductsScreen.name}/${list.id}")
                    },
                    onIconClick = { list ->
                        selectedList = list
                        showBottomSheet = true
                    },
                    onItemOpened = { list ->
                        selectedList = list
                    },
                    onRemove = {
                        showRemoveShoppingListDialog.value = true
                    },
                    onRename = {
                        showEditShoppingListDialog.value = true
                    },
                    onCopy = {
                        selectedList?.let {
                            viewModel.processIntent(ShoppingListIntent.AddShoppingList(name = it.name, icon = it.iconResId.toLong()))
                        }
                    },
                )
                NoData(
                    visible = state.status == ShoppingListState.Status.NO_SHOPPING_LISTS,
                    image = SLTheme.images.noShoppingList,
                    title = stringResource(R.string.no_shopping_lists_title),
                    message = stringResource(R.string.no_shopping_lists_message),
                )
                ShoppingListDialog(
                    visible = showAddShoppingListDialog.value,
                    textStyle = MaterialTheme.typography.headlineSmall,
                    topIcon = R.drawable.ic_add_shopping_list,
                    title = stringResource(R.string.add_shopping_list),
                    confirmText = stringResource(R.string.create),
                    onDismiss = { showAddShoppingListDialog.value = false },
                    onConfirm = { name ->
                        viewModel.processIntent(ShoppingListIntent.AddShoppingList(name = name, icon = R.drawable.ic_list.toLong()))
                    }
                )
                ShoppingListDialog(
                    visible = showEditShoppingListDialog.value,
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.edit_shopping_list),
                    confirmText = stringResource(R.string.edit),
                    text = selectedList?.name,
                    onDismiss = { showEditShoppingListDialog.value = false },
                    onConfirm = { name ->
                        selectedList?.let {
                            /*selectedList =*/ viewModel.processIntent(ShoppingListIntent.UpdateShoppingList(list = it.copy(name = name)))
                        }
                    }
                )
                RemoveShoppingListDialog(
                    visible = showRemoveShoppingListDialog.value,
                    title = "${stringResource(R.string.remove_shopping_list)} ${selectedList?.name}?",
                    onDismiss = { showRemoveShoppingListDialog.value = false },
                    onConfirm = {
                        viewModel.processIntent(BaseIntent.QueryRemoveShoppingList)
                    }
                )
                RemoveShoppingListDialog(
                    visible = showRemoveAllShoppingListsDialog.value,
                    title = stringResource(R.string.remove_all_shopping_lists),
                    onDismiss = { showRemoveAllShoppingListsDialog.value = false },
                    onConfirm = {
                        viewModel.processIntent(ShoppingListIntent.RemoveAllShoppingLists)
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
    viewModel: MainScreenViewModel,
    state: ShoppingListState,
    onItemClick: (ListItem) -> Unit,
    onIconClick: (ListItem) -> Unit = {},
    onItemOpened: (ListItem) -> Unit,
    onRemove: () -> Unit,
    onRename: () -> Unit = {},
    onCopy: () -> Unit = {},
) {
    if (!visible) return

    val items = when(state.status) {
        ShoppingListState.Status.CONTENT -> state.content
        else -> null
    }

    if (items == null) return

    val openList = remember { mutableStateOf<ListItem?>(null) }

    LazyColumn(
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.padding_4x))
            .fillMaxSize(),
    ) {
        items(items, key = { it.id }) { item ->
            ItemList(
                onIntent = { intent ->
                    viewModel.processIntent(intent as ShoppingListIntent)
                },
                action = viewModel.action,
                item = item,
                openList = openList as MutableState<BaseItem?>,
                onItemClick = {
                    if (openList.value?.id != item.id) {
                        onItemClick(item)
                    }
                    openList.value = null
                },
                onIconClick = { onIconClick(item) },
                onItemOpened = { list ->
                    openList.value = list as ListItem
                    onItemOpened(list)

                },
                onItemClosed = { if (openList.value?.id == item.id) openList.value = null },
                onRemove = onRemove,
                onRename = onRename,
                onCopy = onCopy,
            )
        }
    }
}

@Composable
fun SearchShoppingList(
    visible: Boolean,
    state: ShoppingListState,
    onItemClick: () -> Unit,
) {
    if (!visible) return

    val items = when(state.status) {
        ShoppingListState.Status.SEARCH_RESULTS -> state.results
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
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_image)),
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_4x)),
        )
    }
}

@Composable
fun ShoppingListDialog(
    visible: Boolean,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    topIcon: Int? = null,
    title: String,
    confirmText: String,
    text: String? = null,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    if (!visible) return

    var shoppingListName by remember {
        mutableStateOf(
            text?.let {
                text
            } ?: ""
        )
    }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = MaterialTheme.shapes.large,
        icon = {
            topIcon?.let {
                Icon(
                    painter = painterResource(id = topIcon),
                    contentDescription = null
                )
            }
        },
        title = {
            Text(
                text = title,
                style = textStyle,
                modifier = modifier
            )
        },
        text = {
            OutlinedTextField(
                value = shoppingListName,
                onValueChange = { shoppingListName = it },
                label = { Text(stringResource(R.string.shopping_list_name)) },
                placeholder = { Text(stringResource(R.string.new_shopping_list)) },
                maxLines = 1,
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
                Text(confirmText)
            }
        },
    )
}

@Composable
fun RemoveShoppingListDialog(
    visible: Boolean,
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (!visible) return

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = MaterialTheme.shapes.large,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_alert),
                contentDescription = null
            )
        },
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center
            )
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text(stringResource(R.string.remove))
            }
        },
    )
}