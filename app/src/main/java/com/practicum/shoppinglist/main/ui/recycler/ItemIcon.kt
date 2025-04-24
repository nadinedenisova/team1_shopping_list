package com.practicum.shoppinglist.main.ui.recycler

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.practicum.shoppinglist.R

@Composable
fun ItemIcon(
    modifier: Modifier = Modifier,
    icon: Int,
    onClick: () -> Unit = {},
) {
    Box(
        Modifier.wrapContentSize()
    ) {
        Box(
            modifier = modifier
                .size(dimensionResource(R.dimen.icon_size))
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                alignment = Alignment.Center,
                painter = painterResource(id = icon),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer),
                contentDescription = null,
            )
        }
    }
}