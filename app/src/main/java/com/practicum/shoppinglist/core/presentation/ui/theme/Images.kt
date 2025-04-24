package com.practicum.shoppinglist.core.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

@Immutable
data class SLImages(
    val onboardLogo: Int = -1,
    val onboard: Int = -1,
    val noShoppingList: Int = -1,
    val nothingFound: Int = -1,
    val noProductList: Int = -1,
)

val LocalImages = staticCompositionLocalOf { SLImages() }

@Composable
fun ProvideSLImages(
    slImages: SLImages,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalImages provides slImages,
        content = content
    )
}