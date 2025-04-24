package com.practicum.shoppinglist.core.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme

@Composable
fun SLIconButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = SLTheme.slColorScheme.materialScheme.secondaryContainer,
            contentColor = SLTheme.slColorScheme.materialScheme.onSecondaryContainer,
            disabledContainerColor = SLTheme.slColorScheme.materialScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = SLTheme.slColorScheme.materialScheme.onSecondaryContainer.copy(alpha = 0.12f),
        ),
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
        )
    }
}

@Preview(device = PIXEL_6)
@Composable
fun IconButtonPreview() {
    SLTheme {
        Surface {
            Column {
                SLIconButton(icon = painterResource(R.drawable.ic_ski))
                SLIconButton(icon = painterResource(R.drawable.ic_back), enabled = false)
            }
        }
    }
}