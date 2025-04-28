package com.practicum.shoppinglist.main.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.practicum.shoppinglist.core.presentation.ui.FabViewModel
import com.practicum.shoppinglist.details.presentation.ui.DetailsScreen
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Routes.MainScreen.name,
    isSearchActive: MutableState<Boolean>,
    showAddShoppingListDialog: MutableState<Boolean>,
    showRemoveAllShoppingListsDialog: MutableState<Boolean>,
    showMenuBottomSheet: MutableState<Boolean>,
    modifier: Modifier,
    viewModel: MainScreenViewModel,
    fabViewModel: FabViewModel,
    ) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(
            route = Routes.MainScreen.name
        ) {
            MainScreen(
                navController = navController,
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
            val shoppingListId = navBackStackEntry.arguments?.getLong("listId") ?: -1L
            DetailsScreen(
                showMenuBottomSheet = showMenuBottomSheet,
                shoppingListId = shoppingListId,
                fabViewModel = fabViewModel,
            )
        }
    }
}