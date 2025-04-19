package com.practicum.shoppinglist.main.ui.recycler

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.practicum.shoppinglist.core.domain.models.ListItem
import androidx.compose.ui.text.style.TextOverflow
import com.practicum.shoppinglist.R

@Composable
fun ItemListSearch(
    list: ListItem,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.padding_4x))
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(dimensionResource(R.dimen.icon_size))
                .background(
                    color = Color.Yellow,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                alignment = Alignment.Center,
                painter = painterResource(id = list.iconResId),
                contentDescription = null,
            )
        }
        Text(
            text = list.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_4x))
        )
    }
}