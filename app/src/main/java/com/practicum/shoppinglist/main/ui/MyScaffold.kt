package com.practicum.shoppinglist.main.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.practicum.shoppinglist.App
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.ShoppingListIntent
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import com.practicum.shoppinglist.di.api.daggerViewModel
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel

@Composable
fun MyScaffold() {
    val navController = rememberNavController()
    val showAddShoppingListDialog = remember { mutableStateOf(false) }
    val isSearchActive = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val factory = remember {
        (context.applicationContext as App).appComponent.viewModelFactory()
    }
    val viewModel = daggerViewModel<MainScreenViewModel>(factory)
    val state by viewModel.shoppingListStateFlow.collectAsStateWithLifecycle()
    var darkTheme by remember { mutableStateOf(false) }

    LaunchedEffect(state.darkTheme) {
        darkTheme = state.darkTheme
    }

    SLTheme(darkTheme = darkTheme) {

        Scaffold(
            topBar = {
                if (!isSearchActive.value) {
                    TopBar(
                        onSearchClick = { isSearchActive.value = true },
                        onRemoveClick = {},
                        onDarkModeClick = {
                            viewModel.processIntent(ShoppingListIntent.ChangeThemeSettings(!darkTheme))
                        }
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showAddShoppingListDialog.value = true
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
                }
            }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                isSearchActive = isSearchActive,
                showAddShoppingListDialog = showAddShoppingListDialog,
                modifier = Modifier.padding(innerPadding),
                viewModel = viewModel,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onSearchClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onDarkModeClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(stringResource(R.string.main_screen_title)) },
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
                    painter = painterResource(id = R.drawable.ic_dark_mode),
                    contentDescription = stringResource(R.string.dark_mode)
                )
            }
        }
    )
}