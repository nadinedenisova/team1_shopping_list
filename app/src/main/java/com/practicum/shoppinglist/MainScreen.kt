package com.practicum.shoppinglist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.practicum.shoppinglist.di.api.daggerViewModel

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val factory = remember {
        (context as App).appComponent.viewModelFactory()
    }
    val viewModel = daggerViewModel<MainScreenViewModel>(factory)
}