package com.practicum.shoppinglist.core.presentation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.practicum.shoppinglist.core.presentation.navigation.Routes

val isActionButtonHidden = listOf(
    Routes.REGISTRATION_SCREEN.route,
    Routes.LOGIN_SCREEN.route,
    Routes.RESTORE_PASSWORD_SCREEN.route,
)

@Composable
fun MyScaffold() {
    /*val navController = rememberNavController()
    val showAddShoppingListDialog = rememberSaveable { mutableStateOf(false) }
    val showRemoveAllShoppingListsDialog = rememberSaveable { mutableStateOf(false) }
    val showProductsScreenMenu = rememberSaveable { mutableStateOf(false) }
    val isSearchActive = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val factory = remember {
        (context.applicationContext as App).appComponent.viewModelFactory()
    }
    val viewModel = daggerViewModel<MainScreenViewModel>(factory)
    val registrationScreenViewModel = daggerViewModel<RegistrationScreenViewModel>(factory)
    val loginViewModel = daggerViewModel<LoginScreenViewModel>(factory)
    val state by viewModel.shoppingListStateFlow.collectAsStateWithLifecycle()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route

    val fabViewModel = daggerViewModel<FabViewModel>(factory)
    val fabState = fabViewModel.fabState.collectAsState().value

    val screensWithoutTopBar = listOf(Routes.SPLASH_SCREEN.route)
    val showTopBar = screensWithoutTopBar.none {
        currentDestination?.startsWith(it) == true
    }

    val screensWithoutFab = listOf(Routes.SPLASH_SCREEN.route)
    val showFab = screensWithoutFab.none {
        currentDestination?.startsWith(it) == true
    }

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    SLTheme(darkTheme = state.darkTheme) {
        Scaffold(
            topBar = {
                if (!isSearchActive.value && showTopBar) {

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
                        currentDestination = currentDestination,
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
                        onLoginClick = {
                            navController.navigate(LoginScreen)
                        },
                        onLogoutClick = {
                            viewModel.logout()
                        },
                        isLoggedIn = isLoggedIn
                    )
                }
            },
            floatingActionButton = {
                if (showFab) {
                    val offset by animateDpAsState(
                        targetValue = -fabState.offsetY,
                    )

                    val icon =
                        if (fabState.isOpenDetailsBottomSheetState != null) Icons.Default.Done else Icons.Default.Add

                    FloatingActionButton(
                        modifier = Modifier.offset(y = offset),
                        onClick = {
                            if (currentDestination?.startsWith(Routes.MAIN_SCREEN.route) == false) {
                                showAddShoppingListDialog.value = true
                            } else {
                                when (fabState.isOpenDetailsBottomSheetState) {
                                    FabState.State.AddProduct.name -> fabViewModel.onIntent(
                                        FabIntent.AddProduct(true)
                                    )

                                    FabState.State.EditProduct.name -> fabViewModel.onIntent(
                                        FabIntent.EditProduct(true)
                                    )

                                    else -> fabViewModel.onIntent(
                                        FabIntent.OpenDetailsBottomSheet(
                                            state = FabState.State.AddProduct.name
                                        )
                                    )
                                }
                            }
                        },
                        shape = MaterialTheme.shapes.small,

                        ) {
                        Icon(icon, contentDescription = stringResource(R.string.add))
                    }
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
                registrationScreenViewModel = registrationScreenViewModel,
                loginViewModel = loginViewModel,
            )
        }
    }*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    darkTheme: Boolean,
    currentDestination: String?,
    isLoggedIn: Boolean,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    onDarkModeClick: () -> Unit = {},
    onMenuClick: () -> Unit,
    onLoginClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    /*val screensWithoutBackButton = listOf(Routes.SPLASH_SCREEN.route, Routes.MAIN_SCREEN.route)
    val showBackButton = screensWithoutBackButton.none {
        currentDestination?.startsWith(it) == true
    }

    TopAppBar(
            title = {
            Text(
                currentDestination?.let {
                    when {
                        it.startsWith(Routes.MAIN_SCREEN.route) -> stringResource(R.string.main_screen_title)
                        it.startsWith(Routes.DETAILS_SCREEN.route) -> stringResource(R.string.products_screen_title)
                        it.startsWith(Routes.REGISTRATION_SCREEN.route) -> stringResource(R.string.registration_screen_title)
                        it.startsWith(Routes.LOGIN_SCREEN.route) -> stringResource(R.string.login_screen_title)
                        it.startsWith(Routes.RESTORE_PASSWORD_SCREEN.route) -> stringResource(R.string.restore_password)
                        else -> ""
                    }
                } ?: ""
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
                Routes.MAIN_SCREEN.route -> {
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

                else -> {
                    if (currentDestination !in isActionButtonHidden) {
                        IconButton(onClick = onMenuClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = null
                            )
                        }
                    }
                }
            }

        }
    )*/
}