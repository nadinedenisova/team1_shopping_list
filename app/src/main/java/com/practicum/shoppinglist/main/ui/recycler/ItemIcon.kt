package com.practicum.shoppinglist.main.ui.recycler

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.practicum.shoppinglist.R

@Composable
fun ItemIcon(
    icon: Int,
    onClick: () -> Unit,
) {
    Box(
        Modifier.wrapContentSize()
    ) {
        Box(
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                .background(
                    color = Color.Yellow,
                    shape = CircleShape
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                alignment = Alignment.Center,
                painter = painterResource(id = icon),
                contentDescription = null,
            )
        }
    }
}