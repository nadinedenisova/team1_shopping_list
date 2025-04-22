package com.practicum.shoppinglist.core.presentation.ui.theme

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode

private val lightScheme = SLColorScheme(
    materialScheme = lightColorScheme(
        primary = light_primary,
        onPrimary = light_on_primary,
        primaryContainer = light_primary_container,
        onPrimaryContainer = light_on_primary_container,
        inversePrimary = light_inverse_primary,
        secondary = light_secondary,
        onSecondary = light_on_secondary,
        secondaryContainer = light_secondary_container,
        onSecondaryContainer = light_on_secondary_container,
        tertiary = light_tertiary,
        onTertiary = light_on_tertiary,
        tertiaryContainer = light_tertiary_container,
        onTertiaryContainer = light_on_tertiary_container,
        background = light_background,
        onBackground = light_on_background,
        surface = light_surface,
        onSurface = light_on_surface,
        surfaceVariant = light_surface_variant,
        onSurfaceVariant = light_on_surface_variant,
        surfaceTint = light_surface_tint,
        inverseSurface = light_inverse_surface,
        inverseOnSurface = light_inverse_on_surface,
        error = light_error,
        onError = light_on_error,
        errorContainer = light_error_container,
        onErrorContainer = light_on_error_container,
        outline = light_outline,
        outlineVariant = light_outline_variant,
        scrim = light_scrim,
        surfaceBright = light_surface_bright,
        surfaceContainer = light_surface_container,
        surfaceContainerHigh = light_surface_container_high,
        surfaceContainerHighest = light_surface_container_highest,
        surfaceContainerLow = light_surface_container_low,
        surfaceContainerLowest = light_surface_container_lowest,
        surfaceDim = light_surface_dim,
    ),
    shadow = light_shadow,
    primaryFixed = light_primary_fixed,
    onPrimaryFixed = light_on_primary_fixed,
    primaryFixedDim = light_primary_fixed_dim,
    onPrimaryFixedVariant = light_on_primary_fixed_variant,
    secondaryFixed = light_secondary_fixed,
    onSecondaryFixed = light_on_secondary_fixed,
    secondaryFixedDim = light_secondary_fixed_dim,
    onSecondaryFixedVariant = light_on_secondary_fixed_variant,
    tertiaryFixed = light_tertiary_fixed,
    onTertiaryFixed = light_on_tertiary_fixed,
    tertiaryFixedDim = light_tertiary_fixed_dim,
    onTertiaryFixedVariant = light_on_tertiary_fixed_variant,
)

private val darkScheme = SLColorScheme(
    materialScheme = darkColorScheme(
        primary = dark_primary,
        onPrimary = dark_on_primary,
        primaryContainer = dark_primary_container,
        onPrimaryContainer = dark_on_primary_container,
        inversePrimary = dark_inverse_primary,
        secondary = dark_secondary,
        onSecondary = dark_on_secondary,
        secondaryContainer = dark_secondary_container,
        onSecondaryContainer = dark_on_secondary_container,
        tertiary = dark_tertiary,
        onTertiary = dark_on_tertiary,
        tertiaryContainer = dark_tertiary_container,
        onTertiaryContainer = dark_on_tertiary_container,
        background = dark_background,
        onBackground = dark_on_background,
        surface = dark_surface,
        onSurface = dark_on_surface,
        surfaceVariant = dark_surface_variant,
        onSurfaceVariant = dark_on_surface_variant,
        surfaceTint = dark_surface_tint,
        inverseSurface = dark_inverse_surface,
        inverseOnSurface = dark_inverse_on_surface,
        error = dark_error,
        onError = dark_on_error,
        errorContainer = dark_error_container,
        onErrorContainer = dark_on_error_container,
        outline = dark_outline,
        outlineVariant = dark_outline_variant,
        scrim = dark_scrim,
        surfaceBright = dark_surface_bright,
        surfaceContainer = dark_surface_container,
        surfaceContainerHigh = dark_surface_container_high,
        surfaceContainerHighest = dark_surface_container_highest,
        surfaceContainerLow = dark_surface_container_low,
        surfaceContainerLowest = dark_surface_container_lowest,
        surfaceDim = dark_surface_dim,
    ),
    shadow = dark_shadow,
    primaryFixed = dark_primary_fixed,
    onPrimaryFixed = dark_on_primary_fixed,
    primaryFixedDim = dark_primary_fixed_dim,
    onPrimaryFixedVariant = dark_on_primary_fixed_variant,
    secondaryFixed = dark_secondary_fixed,
    onSecondaryFixed = dark_on_secondary_fixed,
    secondaryFixedDim = dark_secondary_fixed_dim,
    onSecondaryFixedVariant = dark_on_secondary_fixed_variant,
    tertiaryFixed = dark_tertiary_fixed,
    onTertiaryFixed = dark_on_tertiary_fixed,
    tertiaryFixedDim = dark_tertiary_fixed_dim,
    onTertiaryFixedVariant = dark_on_tertiary_fixed_variant,
)

private val highContrastLightColorScheme = SLColorScheme(
    materialScheme = lightColorScheme(
        primary = light_high_contrast_primary,
        onPrimary = light_high_contrast_on_primary,
        primaryContainer = light_high_contrast_primary_container,
        onPrimaryContainer = light_high_contrast_on_primary_container,
        inversePrimary = light_high_contrast_inverse_primary,
        secondary = light_high_contrast_secondary,
        onSecondary = light_high_contrast_on_secondary,
        secondaryContainer = light_high_contrast_secondary_container,
        onSecondaryContainer = light_high_contrast_on_secondary_container,
        tertiary = light_high_contrast_tertiary,
        onTertiary = light_high_contrast_on_tertiary,
        tertiaryContainer = light_high_contrast_tertiary_container,
        onTertiaryContainer = light_high_contrast_on_tertiary_container,
        background = light_high_contrast_background,
        onBackground = light_high_contrast_on_background,
        surface = light_high_contrast_surface,
        onSurface = light_high_contrast_on_surface,
        surfaceVariant = light_high_contrast_surface_variant,
        onSurfaceVariant = light_high_contrast_on_surface_variant,
        surfaceTint = light_high_contrast_surface_tint,
        inverseSurface = light_high_contrast_inverse_surface,
        inverseOnSurface = light_high_contrast_inverse_on_surface,
        error = light_high_contrast_error,
        onError = light_high_contrast_on_error,
        errorContainer = light_high_contrast_error_container,
        onErrorContainer = light_high_contrast_on_error_container,
        outline = light_high_contrast_outline,
        outlineVariant = light_high_contrast_outline_variant,
        scrim = light_high_contrast_scrim,
        surfaceBright = light_high_contrast_surface_bright,
        surfaceContainer = light_high_contrast_surface_container,
        surfaceContainerHigh = light_high_contrast_surface_container_high,
        surfaceContainerHighest = light_high_contrast_surface_container_highest,
        surfaceContainerLow = light_high_contrast_surface_container_low,
        surfaceContainerLowest = light_high_contrast_surface_container_lowest,
        surfaceDim = light_high_contrast_surface_dim,
    ),
    shadow = light_high_contrast_shadow,
    primaryFixed = light_high_contrast_primary_fixed,
    onPrimaryFixed = light_high_contrast_on_primary_fixed,
    primaryFixedDim = light_high_contrast_primary_fixed_dim,
    onPrimaryFixedVariant = light_high_contrast_on_primary_fixed_variant,
    secondaryFixed = light_high_contrast_secondary_fixed,
    onSecondaryFixed = light_high_contrast_on_secondary_fixed,
    secondaryFixedDim = light_high_contrast_secondary_fixed_dim,
    onSecondaryFixedVariant = light_high_contrast_on_secondary_fixed_variant,
    tertiaryFixed = light_high_contrast_tertiary_fixed,
    onTertiaryFixed = light_high_contrast_on_tertiary_fixed,
    tertiaryFixedDim = light_high_contrast_tertiary_fixed_dim,
    onTertiaryFixedVariant = light_high_contrast_on_tertiary_fixed_variant,
)

private val highContrastDarkColorScheme = SLColorScheme(
    materialScheme = darkColorScheme(
        primary = dark_high_contrast_primary,
        onPrimary = dark_high_contrast_on_primary,
        primaryContainer = dark_high_contrast_primary_container,
        onPrimaryContainer = dark_high_contrast_on_primary_container,
        inversePrimary = dark_high_contrast_inverse_primary,
        secondary = dark_high_contrast_secondary,
        onSecondary = dark_high_contrast_on_secondary,
        secondaryContainer = dark_high_contrast_secondary_container,
        onSecondaryContainer = dark_high_contrast_on_secondary_container,
        tertiary = dark_high_contrast_tertiary,
        onTertiary = dark_high_contrast_on_tertiary,
        tertiaryContainer = dark_high_contrast_tertiary_container,
        onTertiaryContainer = dark_high_contrast_on_tertiary_container,
        background = dark_high_contrast_background,
        onBackground = dark_high_contrast_on_background,
        surface = dark_high_contrast_surface,
        onSurface = dark_high_contrast_on_surface,
        surfaceVariant = dark_high_contrast_surface_variant,
        onSurfaceVariant = dark_high_contrast_on_surface_variant,
        surfaceTint = dark_high_contrast_surface_tint,
        inverseSurface = dark_high_contrast_inverse_surface,
        inverseOnSurface = dark_high_contrast_inverse_on_surface,
        error = dark_high_contrast_error,
        onError = dark_high_contrast_on_error,
        errorContainer = dark_high_contrast_error_container,
        onErrorContainer = dark_high_contrast_on_error_container,
        outline = dark_high_contrast_outline,
        outlineVariant = dark_high_contrast_outline_variant,
        scrim = dark_high_contrast_scrim,
        surfaceBright = dark_high_contrast_surface_bright,
        surfaceContainer = dark_high_contrast_surface_container,
        surfaceContainerHigh = dark_high_contrast_surface_container_high,
        surfaceContainerHighest = dark_high_contrast_surface_container_highest,
        surfaceContainerLow = dark_high_contrast_surface_container_low,
        surfaceContainerLowest = dark_high_contrast_surface_container_lowest,
        surfaceDim = dark_high_contrast_surface_dim,
    ),
    shadow = dark_high_contrast_shadow,
    primaryFixed = dark_high_contrast_primary_fixed,
    onPrimaryFixed = dark_high_contrast_on_primary_fixed,
    primaryFixedDim = dark_high_contrast_primary_fixed_dim,
    onPrimaryFixedVariant = dark_high_contrast_on_primary_fixed_variant,
    secondaryFixed = dark_high_contrast_secondary_fixed,
    onSecondaryFixed = dark_high_contrast_on_secondary_fixed,
    secondaryFixedDim = dark_high_contrast_secondary_fixed_dim,
    onSecondaryFixedVariant = dark_high_contrast_on_secondary_fixed_variant,
    tertiaryFixed = dark_high_contrast_tertiary_fixed,
    onTertiaryFixed = dark_high_contrast_on_tertiary_fixed,
    tertiaryFixedDim = dark_high_contrast_tertiary_fixed_dim,
    onTertiaryFixedVariant = dark_high_contrast_on_tertiary_fixed_variant,
)

private val mediumContrastLightColorScheme = SLColorScheme(
    materialScheme = lightColorScheme(
        primary = light_medium_contrast_primary,
        onPrimary = light_medium_contrast_on_primary,
        primaryContainer = light_medium_contrast_primary_container,
        onPrimaryContainer = light_medium_contrast_on_primary_container,
        inversePrimary = light_medium_contrast_inverse_primary,
        secondary = light_medium_contrast_secondary,
        onSecondary = light_medium_contrast_on_secondary,
        secondaryContainer = light_medium_contrast_secondary_container,
        onSecondaryContainer = light_medium_contrast_on_secondary_container,
        tertiary = light_medium_contrast_tertiary,
        onTertiary = light_medium_contrast_on_tertiary,
        tertiaryContainer = light_medium_contrast_tertiary_container,
        onTertiaryContainer = light_medium_contrast_on_tertiary_container,
        background = light_medium_contrast_background,
        onBackground = light_medium_contrast_on_background,
        surface = light_medium_contrast_surface,
        onSurface = light_medium_contrast_on_surface,
        surfaceVariant = light_medium_contrast_surface_variant,
        onSurfaceVariant = light_medium_contrast_on_surface_variant,
        surfaceTint = light_medium_contrast_surface_tint,
        inverseSurface = light_medium_contrast_inverse_surface,
        inverseOnSurface = light_medium_contrast_inverse_on_surface,
        error = light_medium_contrast_error,
        onError = light_medium_contrast_on_error,
        errorContainer = light_medium_contrast_error_container,
        onErrorContainer = light_medium_contrast_on_error_container,
        outline = light_medium_contrast_outline,
        outlineVariant = light_medium_contrast_outline_variant,
        scrim = light_medium_contrast_scrim,
        surfaceBright = light_medium_contrast_surface_bright,
        surfaceContainer = light_medium_contrast_surface_container,
        surfaceContainerHigh = light_medium_contrast_surface_container_high,
        surfaceContainerHighest = light_medium_contrast_surface_container_highest,
        surfaceContainerLow = light_medium_contrast_surface_container_low,
        surfaceContainerLowest = light_medium_contrast_surface_container_lowest,
        surfaceDim = light_medium_contrast_surface_dim,
    ),
    shadow = light_medium_contrast_shadow,
    primaryFixed = light_medium_contrast_primary_fixed,
    onPrimaryFixed = light_medium_contrast_on_primary_fixed,
    primaryFixedDim = light_medium_contrast_primary_fixed_dim,
    onPrimaryFixedVariant = light_medium_contrast_on_primary_fixed_variant,
    secondaryFixed = light_medium_contrast_secondary_fixed,
    onSecondaryFixed = light_medium_contrast_on_secondary_fixed,
    secondaryFixedDim = light_medium_contrast_secondary_fixed_dim,
    onSecondaryFixedVariant = light_medium_contrast_on_secondary_fixed_variant,
    tertiaryFixed = light_medium_contrast_tertiary_fixed,
    onTertiaryFixed = light_medium_contrast_on_tertiary_fixed,
    tertiaryFixedDim = light_medium_contrast_tertiary_fixed_dim,
    onTertiaryFixedVariant = light_medium_contrast_on_tertiary_fixed_variant,
)

private val mediumContrastDarkColorScheme = SLColorScheme(
    materialScheme = darkColorScheme(
        primary = dark_medium_contrast_primary,
        onPrimary = dark_medium_contrast_on_primary,
        primaryContainer = dark_medium_contrast_primary_container,
        onPrimaryContainer = dark_medium_contrast_on_primary_container,
        inversePrimary = dark_medium_contrast_inverse_primary,
        secondary = dark_medium_contrast_secondary,
        onSecondary = dark_medium_contrast_on_secondary,
        secondaryContainer = dark_medium_contrast_secondary_container,
        onSecondaryContainer = dark_medium_contrast_on_secondary_container,
        tertiary = dark_medium_contrast_tertiary,
        onTertiary = dark_medium_contrast_on_tertiary,
        tertiaryContainer = dark_medium_contrast_tertiary_container,
        onTertiaryContainer = dark_medium_contrast_on_tertiary_container,
        background = dark_medium_contrast_background,
        onBackground = dark_medium_contrast_on_background,
        surface = dark_medium_contrast_surface,
        onSurface = dark_medium_contrast_on_surface,
        surfaceVariant = dark_medium_contrast_surface_variant,
        onSurfaceVariant = dark_medium_contrast_on_surface_variant,
        surfaceTint = dark_medium_contrast_surface_tint,
        inverseSurface = dark_medium_contrast_inverse_surface,
        inverseOnSurface = dark_medium_contrast_inverse_on_surface,
        error = dark_medium_contrast_error,
        onError = dark_medium_contrast_on_error,
        errorContainer = dark_medium_contrast_error_container,
        onErrorContainer = dark_medium_contrast_on_error_container,
        outline = dark_medium_contrast_outline,
        outlineVariant = dark_medium_contrast_outline_variant,
        scrim = dark_medium_contrast_scrim,
        surfaceBright = dark_medium_contrast_surface_bright,
        surfaceContainer = dark_medium_contrast_surface_container,
        surfaceContainerHigh = dark_medium_contrast_surface_container_high,
        surfaceContainerHighest = dark_medium_contrast_surface_container_highest,
        surfaceContainerLow = dark_medium_contrast_surface_container_low,
        surfaceContainerLowest = dark_medium_contrast_surface_container_lowest,
        surfaceDim = dark_medium_contrast_surface_dim,
    ),
    shadow = dark_medium_contrast_shadow,
    primaryFixed = dark_medium_contrast_primary_fixed,
    onPrimaryFixed = dark_medium_contrast_on_primary_fixed,
    primaryFixedDim = dark_medium_contrast_primary_fixed_dim,
    onPrimaryFixedVariant = dark_medium_contrast_on_primary_fixed_variant,
    secondaryFixed = dark_medium_contrast_secondary_fixed,
    onSecondaryFixed = dark_medium_contrast_on_secondary_fixed,
    secondaryFixedDim = dark_medium_contrast_secondary_fixed_dim,
    onSecondaryFixedVariant = dark_medium_contrast_on_secondary_fixed_variant,
    tertiaryFixed = dark_medium_contrast_tertiary_fixed,
    onTertiaryFixed = dark_medium_contrast_on_tertiary_fixed,
    tertiaryFixedDim = dark_medium_contrast_tertiary_fixed_dim,
    onTertiaryFixedVariant = dark_medium_contrast_on_tertiary_fixed_variant,
)

fun isContrastAvailable(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
}

@Composable
fun selectSchemeForContrast(isDark: Boolean): SLColorScheme {
    val context = LocalContext.current
    var colorScheme = if (isDark) darkScheme else lightScheme
    val isPreview = LocalInspectionMode.current

    if (!isPreview && isContrastAvailable()) {
        val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val contrastLevel = uiModeManager.contrast

        colorScheme = when (contrastLevel) {
            in 0.0f..0.33f -> if (isDark)
                darkScheme else lightScheme

            in 0.34f..0.66f -> if (isDark)
                mediumContrastDarkColorScheme else mediumContrastLightColorScheme

            in 0.67f..1.0f -> if (isDark)
                highContrastDarkColorScheme else highContrastLightColorScheme

            else -> if (isDark) darkScheme else lightScheme
        }
        return colorScheme
    } else return colorScheme
}

@Composable
fun SLTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {

    val scheme = selectSchemeForContrast(darkTheme)

    val replyColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current

            val dynamicScheme =
                if (darkTheme) dynamicDarkColorScheme(context)
                else dynamicLightColorScheme(context)

            dynamicScheme.copy(
                primary = scheme.primaryFixed,
                onPrimary = scheme.onPrimaryFixed,
                secondary = scheme.secondaryFixed,
                onSecondary = scheme.onSecondaryFixed,
                tertiary = scheme.tertiaryFixed,
                onTertiary = scheme.onTertiaryFixed,
            ).toSLColorScheme(
                shadow = scheme.shadow,
                primaryFixed = scheme.primaryFixed,
                onPrimaryFixed = scheme.onPrimaryFixed,
                primaryFixedDim = scheme.primaryFixedDim,
                onPrimaryFixedVariant = scheme.onPrimaryFixedVariant,
                secondaryFixed = scheme.secondaryFixed,
                onSecondaryFixed = scheme.onSecondaryFixed,
                secondaryFixedDim = scheme.secondaryFixedDim,
                onSecondaryFixedVariant = scheme.onSecondaryFixedVariant,
                tertiaryFixed = scheme.tertiaryFixed,
                onTertiaryFixed = scheme.onTertiaryFixed,
                tertiaryFixedDim = scheme.tertiaryFixedDim,
                onTertiaryFixedVariant = scheme.onTertiaryFixedVariant,
            )
        }

        else -> scheme
    }

    ProvideSLColors(
        colorScheme = replyColorScheme
    ) {
        MaterialTheme(
            colorScheme = LocalColorScheme.current.materialScheme,
            shapes = shapes,
            typography = typography,
            content = content,
        )
    }
}

val MaterialTheme.slColorScheme: SLColorScheme
    @Composable
    get() = LocalColorScheme.current