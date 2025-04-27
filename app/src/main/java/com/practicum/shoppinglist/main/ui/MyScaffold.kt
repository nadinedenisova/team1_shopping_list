package com.practicum.shoppinglist.main.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.practicum.shoppinglist.App
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.auth.viewmodel.RegistrationViewModel
import com.practicum.shoppinglist.common.resources.ShoppingListIntent
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import com.practicum.shoppinglist.di.api.daggerViewModel
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel

@Composable
fun MyScaffold() {
    val navController = rememberNavController()
    val showAddShoppingListDialog = remember { mutableStateOf(false) }
    val showAddProductListDialog = remember { mutableStateOf(false) }
    val showRemoveAllShoppingListsDialog = rememberSaveable { mutableStateOf(false) }
    val showProductsScreenMenu = rememberSaveable { mutableStateOf(false) }
    val isSearchActive = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val factory = remember {
        (context.applicationContext as App).appComponent.viewModelFactory()
    }
    val viewModel = daggerViewModel<MainScreenViewModel>(factory)
    val registrationViewModel = daggerViewModel<RegistrationViewModel>(factory)
    val state by viewModel.shoppingListStateFlow.collectAsStateWithLifecycle()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    SLTheme(darkTheme = state.darkTheme) {

        Scaffold(
            topBar = {
                if (!isSearchActive.value) {
                    TopBar(
                        darkTheme = state.darkTheme,
                        currentDestination = currentDestination?.route,
                        onBackClick = { navController.popBackStack() },
                        onSearchClick = { isSearchActive.value = true },
                        onRemoveClick = {
                            showRemoveAllShoppingListsDialog.value = true
                        },
                        onDarkModeClick = {
                            viewModel.processIntent(ShoppingListIntent.ChangeThemeSettings(!state.darkTheme))
                        },
                        onMenuClick = {
                            showProductsScreenMenu.value = true
                        },
                        onRegistrationClick = {
                            navController.navigate(Routes.Registration.name)
                        }
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (currentDestination?.route == Routes.MainScreen.name) {
                            showAddShoppingListDialog.value = true
                        } else {
                            showAddProductListDialog.value = true
                        }
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
                }
            }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                isSearchActive = isSearchActive,
                showAddShoppingListDialog = showAddShoppingListDialog,
                showAddProductListDialog = showAddProductListDialog,
                showRemoveAllShoppingListsDialog = showRemoveAllShoppingListsDialog,
                showMenuBottomSheet = showProductsScreenMenu,
                modifier = Modifier.padding(innerPadding),
                viewModel = viewModel,
                registrationViewModel = registrationViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    darkTheme: Boolean,
    currentDestination: String?,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit = {},
    onRegistrationClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    onDarkModeClick: () -> Unit = {},
    onMenuClick: () -> Unit,
) {
    val screensWithoutBackButton = listOf(Routes.MainScreen.name)
    val showBackButton = currentDestination !in screensWithoutBackButton

    TopAppBar(
        title = {
            Text(
                when (currentDestination) {
                    Routes.MainScreen.name -> stringResource(R.string.main_screen_title)
                    Routes.Registration.name -> stringResource(R.string.registration_screen_title)
                    Routes.Login.name -> stringResource(R.string.login_screen_title)
                    else -> stringResource(R.string.products_screen_title)
                }
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
            }
        },
        actions = {
            when (currentDestination) {
                Routes.MainScreen.name -> {
                    Button (onClick = onRegistrationClick) {
                        Text(stringResource(R.string.registration))
                    }
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
                            painter = if (darkTheme) painterResource(id = R.drawable.ic_light_theme) else painterResource(id = R.drawable.ic_dark_mode),
                            contentDescription = stringResource(R.string.dark_mode)
                        )
                    }
                }
                else -> {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu),
                            contentDescription = null
                        )
                    }
                }
            }

        }
    )
}