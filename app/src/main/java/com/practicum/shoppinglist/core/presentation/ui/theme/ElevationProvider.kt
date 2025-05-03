package com.practicum.shoppinglist.core.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Elevation(
    val level0: Dp = 0.dp,
    val level1: Dp = 1.dp,
    val level2: Dp = 3.dp,
    val level3: Dp = 6.dp,
    val level4: Dp = 8.dp,
    val level5: Dp = 12.dp,
)

val LocalElevation = staticCompositionLocalOf {
    Elevation()
}

@Composable
fun ProvideElevation(
    elevation: Elevation,
    content: @Composable () -> Unit,
) {
    val elevationCache = remember { elevation }

    CompositionLocalProvider(
        LocalElevation provides elevationCache,
        content = content
    )
}