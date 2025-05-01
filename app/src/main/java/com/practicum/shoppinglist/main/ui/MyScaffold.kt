package com.practicum.shoppinglist.main.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.practicum.shoppinglist.App
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.ShoppingListIntent
import com.practicum.shoppinglist.core.presentation.ui.FabViewModel
import com.practicum.shoppinglist.core.presentation.ui.state.FabIntent
import com.practicum.shoppinglist.core.presentation.ui.state.FabState
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import com.practicum.shoppinglist.di.api.daggerViewModel
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel

@Composable
fun MyScaffold() {
    val navController = rememberNavController()
    val showAddShoppingListDialog = remember { mutableStateOf(false) }
    val showRemoveAllShoppingListsDialog = rememberSaveable { mutableStateOf(false) }
    val showProductsScreenMenu = rememberSaveable { mutableStateOf(false) }
    val isSearchActive = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val factory = remember {
        (context.applicationContext as App).appComponent.viewModelFactory()
    }
    val viewModel = daggerViewModel<MainScreenViewModel>(factory)
    val state by viewModel.shoppingListStateFlow.collectAsStateWithLifecycle()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val fabViewModel = daggerViewModel<FabViewModel>(factory)
    val fabState = fabViewModel.fabState.collectAsState().value

    SLTheme(darkTheme = state.darkTheme) {
        Scaffold(
            topBar = {
                if (!isSearchActive.value) {

                    if (fabState.isOpenDetailsBottomSheetState != null) {
                        val height = WindowInsets.statusBars.asPaddingValues()

                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(height.calculateTopPadding() + 64.dp)
                                .background(Color.Black.copy(alpha = .32f))
                                .clickable(
                                    interactionSource = null,
                                    indication = null,
                                ) { fabViewModel.onIntent(FabIntent.CloseDetailsBottomSheet) }
                                .zIndex(Float.MAX_VALUE)
                        )
                    }

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
                        }
                    )
                }
            },
            floatingActionButton = {
                val offset by animateDpAsState(
                    targetValue = -fabState.offsetY,
                )

                val icon = if (fabState.isOpenDetailsBottomSheetState != null) Icons.Default.Done else Icons.Default.Add

                FloatingActionButton(
                    modifier = Modifier.offset(y = offset),
                    onClick = {
                        if (currentDestination?.route == Routes.MainScreen.name) {
                            showAddShoppingListDialog.value = true
                        } else {
                            when (fabState.isOpenDetailsBottomSheetState) {
                                FabState.State.AddProduct.name -> fabViewModel.onIntent(FabIntent.AddProduct(true))
                                FabState.State.EditProduct.name -> fabViewModel.onIntent(FabIntent.EditProduct(true))
                                else -> fabViewModel.onIntent(FabIntent.OpenDetailsBottomSheet(state = FabState.State.AddProduct.name))
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.small,

                    ) {
                    Icon(icon, contentDescription = stringResource(R.string.add))
                }
            }
        ) { innerPadding ->
            NavGraph(
                navController = navController,
                isSearchActive = isSearchActive,
                showAddShoppingListDialog = showAddShoppingListDialog,
                showRemoveAllShoppingListsDialog = showRemoveAllShoppingListsDialog,
                showMenuBottomSheet = showProductsScreenMenu,
                modifier = Modifier.padding(innerPadding),
                viewModel = viewModel,
                fabViewModel = fabViewModel,
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