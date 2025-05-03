package com.practicum.shoppinglist.core.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToMainScreen: () -> Unit,
) {

    LaunchedEffect(Unit) {
        delay(2000L)
        onNavigateToMainScreen()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            alignment = Alignment.Center,
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
        )

        NoData(
            visible = true,
            modifier = Modifier
                .padding(top = 94.dp)
                .fillMaxWidth(),
            image = R.drawable.splash_screen,
            title = stringResource(R.string.splash_screen_title),
            message = stringResource(R.string.splash_screen_message),
        )
    }
}