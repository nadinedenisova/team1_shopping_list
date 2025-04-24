package com.practicum.shoppinglist.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Routes.MainScreen.name,
    isSearchActive: MutableState<Boolean>,
    showAddShoppingListDialog: MutableState<Boolean>,
    showRemoveAllShoppingListsDialog: MutableState<Boolean>,
    modifier: Modifier,
    viewModel: MainScreenViewModel,
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(
            route = Routes.MainScreen.name
        ) {
            MainScreen(
                viewModel = viewModel,
                isSearchActive = isSearchActive,
                showAddShoppingListDialog = showAddShoppingListDialog,
                showRemoveAllShoppingListsDialog = showRemoveAllShoppingListsDialog,
            )
        }

        composable(
            route = "${Routes.ProductsScreen.name}/{listId}",
            arguments = listOf(
                navArgument("listId") { type = NavType.LongType }
            )
        ) {  navBackStackEntry ->
            val listId = navBackStackEntry.arguments?.getLong("listId") ?: -1L

        }
    }
}