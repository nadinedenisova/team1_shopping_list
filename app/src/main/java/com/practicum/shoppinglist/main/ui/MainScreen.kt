package com.practicum.shoppinglist.main.ui

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.common.resources.ListAction
import com.practicum.shoppinglist.common.resources.ShoppingListIntent
import com.practicum.shoppinglist.common.resources.ShoppingListState
import com.practicum.shoppinglist.core.domain.models.BaseItem
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.core.presentation.ui.components.SLInfo
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import com.practicum.shoppinglist.main.ui.recycler.ItemList
import com.practicum.shoppinglist.main.ui.recycler.ItemListSearch
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel,
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToDetailsScreen: (Long) -> Unit,
) {
    val shoppingListState by mainScreenViewModel.shoppingListStateFlow.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val isSearchActive = rememberSaveable { mutableStateOf(false) }

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val showAddShoppingListDialog = rememberSaveable { mutableStateOf(false) }
    val showRemoveAllShoppingListsDialog = rememberSaveable { mutableStateOf(false) }
    val showEditShoppingListDialog = rememberSaveable { mutableStateOf(false) }
    val showRemoveShoppingListDialog = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isSearchActive.value) {
        if (!isSearchActive.value) {
            searchQuery.value = ""
            mainScreenViewModel.processIntent(ShoppingListIntent.ClearSearchResults)
        }
    }

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
               mainScreenViewModel.processIntent(ShoppingListIntent.UpdateShoppingList(list = shoppingListState.selectedList.copy(iconResId = icon)))
            },
        )
    }

    Scaffold(
        topBar = {
            if (!isSearchActive.value) {

                TopBar(
                    darkTheme = shoppingListState.darkTheme,
                    onSearchClick = { isSearchActive.value = true },
                    onRemoveClick = {
                        showRemoveAllShoppingListsDialog.value = true
                    },
                    onDarkModeClick = {
                       mainScreenViewModel.processIntent(ShoppingListIntent.ChangeThemeSettings(!shoppingListState.darkTheme))
                    },
                    onLoginClick = onNavigateToLoginScreen,
                    onLogoutClick = {
                        mainScreenViewModel.processIntent(ShoppingListIntent.Logout)
                    },
                    isLoggedIn = shoppingListState.loggedIn
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddShoppingListDialog.value = true
                },
                shape = MaterialTheme.shapes.small,
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
            }
        }
    ) { innerPadding ->

        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxWidth()
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
                    },
                    onValueChange = { newValue ->
                        searchQuery.value = newValue
                        mainScreenViewModel.processIntent(ShoppingListIntent.Search(searchQuery = searchQuery.value))
                    },
                    onBtnClearClick = {
                        searchQuery.value = ""
                        mainScreenViewModel.processIntent(ShoppingListIntent.ClearSearchResults)
                    },
                )
                if (isSearchActive.value && searchQuery.value.isNotEmpty() && shoppingListState.results.isNotEmpty()) {
                    SearchShoppingList(
                        state = shoppingListState,
                        onItemClick = { list ->
                            isSearchActive.value = false
                            onNavigateToDetailsScreen(list.id)
                        }
                    )
                }
                if (shoppingListState.nothingFound) {
                    SLInfo(
                        modifier = Modifier.padding(top = 64.dp).fillMaxSize(),
                        image = R.drawable.nothing_found_light,
                        title = stringResource(R.string.nothing_found_title),
                        message = stringResource(R.string.nothing_found_message),
                    )
                }
                Box {
                    if (shoppingListState.content.isNotEmpty()) {
                        ShoppingList(
                            onIntent = { intent ->
                                mainScreenViewModel.processIntent(intent)
                            },
                            action = mainScreenViewModel.action,
                            state = shoppingListState,
                            onItemClick = { list ->
                                onNavigateToDetailsScreen(list.id)
                            },
                            onIconClick = { list ->
                                mainScreenViewModel.processIntent(
                                    ShoppingListIntent.SelectedList(
                                        list
                                    )
                                )
                                showBottomSheet = true
                            },
                            onRemove = {
                                showRemoveShoppingListDialog.value = true
                            },
                            onRename = {
                                showEditShoppingListDialog.value = true
                            },
                            onCopy = {
                                shoppingListState.selectedList.also {
                                    mainScreenViewModel.processIntent(
                                        ShoppingListIntent.CopyShoppingList(
                                            list = it
                                        )
                                    )
                                }
                            },
                        )
                    }
                    if (shoppingListState.content.isEmpty()) {
                        SLInfo(
                            image = SLTheme.images.noShoppingList,
                            title = stringResource(R.string.no_shopping_lists_title),
                            message = stringResource(R.string.no_shopping_lists_message),
                        )
                    }
                    ShoppingListDialog(
                        visible = showAddShoppingListDialog.value,
                        textStyle = MaterialTheme.typography.headlineSmall,
                        topIcon = R.drawable.ic_add_shopping_list,
                        title = stringResource(R.string.add_shopping_list),
                        confirmText = stringResource(R.string.create),
                        onDismiss = { showAddShoppingListDialog.value = false },
                        onConfirm = { name ->
                           mainScreenViewModel.processIntent(
                                ShoppingListIntent.AddShoppingList(
                                    name = name,
                                    icon = R.drawable.ic_list.toLong()
                                )
                            )
                        }
                    )
                    ShoppingListDialog(
                        visible = showEditShoppingListDialog.value,
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(R.string.edit_shopping_list),
                        confirmText = stringResource(R.string.edit),
                        text = shoppingListState.selectedList.name,
                        onDismiss = { showEditShoppingListDialog.value = false },
                        onConfirm = { name ->
                           mainScreenViewModel.processIntent(
                                ShoppingListIntent.UpdateShoppingList(
                                    list = shoppingListState.selectedList.copy(
                                        name = name
                                    )
                                )
                            )
                        }
                    )
                    RemoveShoppingListDialog(
                        visible = showRemoveShoppingListDialog.value,
                        title = stringResource(
                            R.string.remove_shopping_list,
                            shoppingListState.selectedList.name
                        ),
                        onDismiss = { showRemoveShoppingListDialog.value = false },
                        onConfirm = {
                           mainScreenViewModel.processIntent(BaseIntent.QueryRemoveShoppingList)
                        }
                    )
                    RemoveShoppingListDialog(
                        visible = showRemoveAllShoppingListsDialog.value,
                        title = stringResource(R.string.remove_all_shopping_lists),
                        onDismiss = { showRemoveAllShoppingListsDialog.value = false },
                        onConfirm = {
                           mainScreenViewModel.processIntent(ShoppingListIntent.RemoveAllShoppingLists)
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
    onIntent: (ShoppingListIntent) -> Unit,
    action: SharedFlow<ListAction>,
    state: ShoppingListState,
    onItemClick: (ListItem) -> Unit,
    onIconClick: (ListItem) -> Unit = {},
    onRemove: () -> Unit,
    onRename: () -> Unit = {},
    onCopy: () -> Unit = {},
) {
    val items = state.content
    val openList = remember { mutableStateOf<BaseItem?>(null) }

    LazyColumn(
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.padding_4x))
            .fillMaxSize(),
    ) {
        items(items, key = { it.id }) { item ->
            ItemList(
                onIntent = { intent ->
                    onIntent(intent)
                },
                action = action,
                item = item,
                openList = openList,
                onItemClick = {
                    if (openList.value?.id != item.id) {
                        onItemClick(item)
                    }
                    openList.value = null
                },
                onIconClick = { onIconClick(item) },
                onItemOpened = {
                    openList.value = item
                    onIntent(ShoppingListIntent.SelectedList(item))

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
    state: ShoppingListState,
    onItemClick: (ListItem) -> Unit,
) {
    val items = state.results

    LazyColumn(
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.padding_4x))
            .fillMaxSize(),
    ) {
        items(items) { item ->
            ItemListSearch(
                list = item,
                onItemClick = { onItemClick(item) },
            )
        }
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
        mutableStateOf(text ?: "")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    darkTheme: Boolean,
    isLoggedIn: Boolean,
    onSearchClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    onDarkModeClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {

    TopAppBar(
        title = {
            Text(stringResource(R.string.main_screen_title))
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search)
                )
            }
            IconButton(onClick = onRemoveClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_remove),
                    contentDescription = stringResource(R.string.remove)
                )
            }
            IconButton(onClick = onDarkModeClick) {
                Icon(
                    painter = if (darkTheme) painterResource(id = R.drawable.ic_light_theme) else painterResource(
                        id = R.drawable.ic_dark_mode
                    ),
                    contentDescription = stringResource(R.string.dark_mode)
                )
            }
            
            if (!isLoggedIn) {
                Button(onClick = onLoginClick) {
                    Text(stringResource(R.string.login))
                }
            } else {
                Button(onClick = onLogoutClick) {
                    Text(stringResource(R.string.logout))
                }
            }
        }
    )
}