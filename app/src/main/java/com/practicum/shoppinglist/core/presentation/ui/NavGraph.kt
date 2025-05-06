package com.practicum.shoppinglist.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.practicum.shoppinglist.auth.ui.LoginScreen
import com.practicum.shoppinglist.auth.ui.RegistrationScreen
import com.practicum.shoppinglist.auth.ui.RestorePasswordScreen
import com.practicum.shoppinglist.auth.viewmodel.LoginScreenViewModel
import com.practicum.shoppinglist.auth.viewmodel.RegistrationScreenViewModel
import com.practicum.shoppinglist.core.presentation.navigation.DetailsScreen
import com.practicum.shoppinglist.core.presentation.navigation.LoginScreen
import com.practicum.shoppinglist.core.presentation.navigation.MainScreen
import com.practicum.shoppinglist.core.presentation.navigation.RegistrationScreen
import com.practicum.shoppinglist.core.presentation.navigation.RestorePasswordScreen
import com.practicum.shoppinglist.core.presentation.navigation.SplashScreen
import com.practicum.shoppinglist.details.presentation.ui.DetailsScreen
import com.practicum.shoppinglist.main.ui.MainScreen
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    isSearchActive: MutableState<Boolean>,
    showAddShoppingListDialog: MutableState<Boolean>,
    showRemoveAllShoppingListsDialog: MutableState<Boolean>,
    showMenuBottomSheet: MutableState<Boolean>,
    modifier: Modifier,
    viewModel: MainScreenViewModel,
    fabViewModel: FabViewModel,
    registrationScreenViewModel: RegistrationScreenViewModel,
    loginViewModel: LoginScreenViewModel,
) {

    NavHost(
        navController = navController,
        startDestination = SplashScreen,
        modifier = modifier,

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
                onNavigateToDetailsScreen = { listId ->
                    navController.navigate(route = DetailsScreen(listId = listId))
                },
                viewModel = viewModel,
                isSearchActive = isSearchActive,
                showAddShoppingListDialog = showAddShoppingListDialog,
                showRemoveAllShoppingListsDialog = showRemoveAllShoppingListsDialog,
            )
        }

        composable<DetailsScreen> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<DetailsScreen>()
            DetailsScreen(
                showMenuBottomSheet = showMenuBottomSheet,
                shoppingListId = args.listId,
                fabViewModel = fabViewModel,
            )
        }

        composable<RegistrationScreen>{
            RegistrationScreen(
                callback = {
                    navController.navigate(MainScreen)
                },
                registrationScreenViewModel
            )
        }

        composable<LoginScreen>{
            LoginScreen(
                navController = navController,
                loginViewModel
            )
        }

        composable<RestorePasswordScreen> {
            RestorePasswordScreen(
                navController = navController,
            )
        }
    }
}