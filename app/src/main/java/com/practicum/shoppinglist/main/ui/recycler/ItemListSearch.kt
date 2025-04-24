package com.practicum.shoppinglist.main.ui.recycler

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.core.domain.models.ListItem

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
        ItemIcon(icon = list.iconResId,)
        Text(
            text = list.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_4x))
        )
    }
}