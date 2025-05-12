package com.practicum.shoppinglist.core.presentation.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import com.practicum.shoppinglist.details.presentation.ui.DetailsScreen
import com.practicum.shoppinglist.main.ui.MainScreen
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun TabletListDetailScreen(
    mainScreenViewModel: MainScreenViewModel,
    fabViewModel: FabViewModel,
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToMainScreen: () -> Unit,
) {
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    NavigableListDetailPaneScaffold(
        navigator = scaffoldNavigator,
        listPane = {
            AnimatedPane {
                MainScreen(
                    mainScreenViewModel = mainScreenViewModel,
                    onNavigateToLoginScreen = onNavigateToLoginScreen,
                    onItemClick = { id ->
                        scope.launch {
                            scaffoldNavigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                content = id
                            )
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val listId = scaffoldNavigator.currentDestination?.content as? Long ?: -1L
                DetailsScreen(
                    shoppingListId = listId,
                    fabViewModel = fabViewModel,
                    onNavigateToMainScreen = if (!isTablet) onNavigateToMainScreen else null
                )
            }
        }
    )
}
