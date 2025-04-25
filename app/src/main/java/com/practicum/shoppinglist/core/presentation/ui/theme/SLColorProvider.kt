package com.practicum.shoppinglist.core.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SLColorScheme(
    val materialScheme: ColorScheme,

    val shadow: Color,

    val primaryFixed: Color,
    val onPrimaryFixed: Color,
    val primaryFixedDim: Color,
    val onPrimaryFixedVariant: Color,

    val secondaryFixed: Color,
    val onSecondaryFixed: Color,
    val secondaryFixedDim: Color,
    val onSecondaryFixedVariant: Color,

    val tertiaryFixed: Color,
    val onTertiaryFixed: Color,
    val tertiaryFixedDim: Color,
    val onTertiaryFixedVariant: Color,
)


fun ColorScheme.toSLColorScheme(
    shadow: Color,
    primaryFixed: Color,
    onPrimaryFixed: Color,
    primaryFixedDim: Color,
    onPrimaryFixedVariant: Color,
    secondaryFixed: Color,
    onSecondaryFixed: Color,
    secondaryFixedDim: Color,
    onSecondaryFixedVariant: Color,
    tertiaryFixed: Color,
    onTertiaryFixed: Color,
    tertiaryFixedDim: Color,
    onTertiaryFixedVariant: Color,
) = SLColorScheme(
    materialScheme = this,
    shadow = shadow,
    primaryFixed = primaryFixed,
    onPrimaryFixed = onPrimaryFixed,
    primaryFixedDim = primaryFixedDim,
    onPrimaryFixedVariant = onPrimaryFixedVariant,
    secondaryFixed = secondaryFixed,
    onSecondaryFixed = onSecondaryFixed,
    secondaryFixedDim = secondaryFixedDim,
    onSecondaryFixedVariant = onSecondaryFixedVariant,
    tertiaryFixed = tertiaryFixed,
    onTertiaryFixed = onTertiaryFixed,
    tertiaryFixedDim = tertiaryFixedDim,
    onTertiaryFixedVariant = onTertiaryFixedVariant,
)

val LocalColorScheme = staticCompositionLocalOf {
    SLColorScheme(
        materialScheme = ColorScheme(
            primary = Color.Unspecified,
            onPrimary = Color.Unspecified,
            primaryContainer = Color.Unspecified,
            onPrimaryContainer = Color.Unspecified,
            inversePrimary = Color.Unspecified,
            secondary = Color.Unspecified,
            onSecondary = Color.Unspecified,
            secondaryContainer = Color.Unspecified,
            onSecondaryContainer = Color.Unspecified,
            tertiary = Color.Unspecified,
            onTertiary = Color.Unspecified,
            tertiaryContainer = Color.Unspecified,
            onTertiaryContainer = Color.Unspecified,
            background = Color.Unspecified,
            onBackground = Color.Unspecified,
            surface = Color.Unspecified,
            onSurface = Color.Unspecified,
            surfaceVariant = Color.Unspecified,
            onSurfaceVariant = Color.Unspecified,
            surfaceTint = Color.Unspecified,
            inverseSurface = Color.Unspecified,
            inverseOnSurface = Color.Unspecified,
            error = Color.Unspecified,
            onError = Color.Unspecified,
            errorContainer = Color.Unspecified,
            onErrorContainer = Color.Unspecified,
            outline = Color.Unspecified,
            outlineVariant = Color.Unspecified,
            scrim = Color.Unspecified,
            surfaceBright = Color.Unspecified,
            surfaceContainer = Color.Unspecified,
            surfaceContainerHigh = Color.Unspecified,
            surfaceContainerHighest = Color.Unspecified,
            surfaceContainerLow = Color.Unspecified,
            surfaceContainerLowest = Color.Unspecified,
            surfaceDim = Color.Unspecified,
        ),
        shadow = Color.Unspecified,
        primaryFixed = Color.Unspecified,
        onPrimaryFixed = Color.Unspecified,
        primaryFixedDim = Color.Unspecified,
        onPrimaryFixedVariant = Color.Unspecified,
        secondaryFixed = Color.Unspecified,
        onSecondaryFixed = Color.Unspecified,
        secondaryFixedDim = Color.Unspecified,
        onSecondaryFixedVariant = Color.Unspecified,
        tertiaryFixed = Color.Unspecified,
        onTertiaryFixed = Color.Unspecified,
        tertiaryFixedDim = Color.Unspecified,
        onTertiaryFixedVariant = Color.Unspecified,
    )
}

@Composable
fun ProvideSLColors(
    colorScheme: SLColorScheme,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        content = content
    )
}