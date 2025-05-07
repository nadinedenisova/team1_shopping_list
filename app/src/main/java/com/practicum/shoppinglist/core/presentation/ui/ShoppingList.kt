package com.practicum.shoppinglist.core.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.practicum.shoppinglist.App
import com.practicum.shoppinglist.auth.ui.LoginScreen
import com.practicum.shoppinglist.auth.ui.RegistrationScreen
import com.practicum.shoppinglist.auth.ui.RestorePasswordScreen
import com.practicum.shoppinglist.auth.viewmodel.LoginScreenViewModel
import com.practicum.shoppinglist.auth.viewmodel.RecoveryScreenViewModel
import com.practicum.shoppinglist.auth.viewmodel.RegistrationScreenViewModel
import com.practicum.shoppinglist.core.presentation.navigation.DetailsScreen
import com.practicum.shoppinglist.core.presentation.navigation.LoginScreen
import com.practicum.shoppinglist.core.presentation.navigation.MainScreen
import com.practicum.shoppinglist.core.presentation.navigation.RegistrationScreen
import com.practicum.shoppinglist.core.presentation.navigation.RestorePasswordScreen
import com.practicum.shoppinglist.core.presentation.navigation.SplashScreen
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import com.practicum.shoppinglist.details.presentation.ui.DetailsScreen
import com.practicum.shoppinglist.di.api.daggerViewModel
import com.practicum.shoppinglist.main.ui.MainScreen
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel

@Composable
fun ShoppingList() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val factory = remember {
        (context.applicationContext as App).appComponent.viewModelFactory()
    }

    val mainScreenViewModel = daggerViewModel<MainScreenViewModel>(factory)
    val registrationScreenViewModel = daggerViewModel<RegistrationScreenViewModel>(factory)
    val recoveryScreenViewModel = daggerViewModel<RecoveryScreenViewModel>(factory)
    val loginViewModel = daggerViewModel<LoginScreenViewModel>(factory)
    val fabViewModel = daggerViewModel<FabViewModel>(factory)

    val state by mainScreenViewModel.shoppingListStateFlow.collectAsStateWithLifecycle()

    SLTheme(darkTheme = state.darkTheme) {
        Box {
            NavHost(
                navController = navController,
                startDestination = SplashScreen,
            ) {
                composable<SplashScreen> {
                    SplashScreen(
                        onNavigateToMainScreen = {
                            navController.navigate(MainScreen) {
                                popUpTo(SplashScreen) { inclusive = true }
                            }
                        },
                    )
                }

                composable<MainScreen> {
                    MainScreen(
                        mainScreenViewModel = mainScreenViewModel,
                        onNavigateToLoginScreen = {
                            navController.navigate(LoginScreen)
                        },
                        onNavigateToDetailsScreen = { listId ->
                            navController.navigate(route = DetailsScreen(listId = listId))
                        },
                    )
                }

                composable<DetailsScreen> { navBackStackEntry ->
                    val args = navBackStackEntry.toRoute<DetailsScreen>()
                    DetailsScreen(
                        shoppingListId = args.listId,
                        fabViewModel = fabViewModel,
                        onNavigateUp = { navController.navigateUp() }
                    )
                }

                composable<RegistrationScreen> {
                    RegistrationScreen(
                        navController,
                        callback = { navController.navigate(MainScreen) },
                        registrationViewModel = registrationScreenViewModel,
                    )
                }

                composable<LoginScreen> {
                    LoginScreen(
                        navController = navController,
                        loginViewModel
                    )
                }

                composable<RestorePasswordScreen> {
                    RestorePasswordScreen(
                        navController = navController,
                        recoveryScreenViewModel = recoveryScreenViewModel,
                    )
                }
            }
        }
    }
}