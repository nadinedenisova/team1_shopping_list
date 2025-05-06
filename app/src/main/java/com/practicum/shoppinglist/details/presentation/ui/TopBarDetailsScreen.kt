package com.practicum.shoppinglist.details.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.practicum.shoppinglist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarDetailsScreen(
    onMenuClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(stringResource(R.string.products_screen_title))
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        actions = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null
                )
            }

        }
    )
}