package com.practicum.shoppinglist.main.ui.recycler

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.common.resources.ListAction
import com.practicum.shoppinglist.core.domain.models.BaseItem
import com.practicum.shoppinglist.core.domain.models.ListItem
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ItemList(
    onIntent: (BaseIntent) -> Unit,
    action: SharedFlow<ListAction>,
    item: ListItem,
    openList: MutableState<BaseItem?>,
    onItemClick: () -> Unit,
    onIconClick: () -> Unit,
    onItemOpened: (BaseItem) -> Unit,
    onItemClosed: () -> Unit,
    onRemove: () -> Unit,
    onRename: () -> Unit,
    onCopy: () -> Unit,
) {
    SwipeItem(
        onIntent = onIntent,
        action = action,
        item = item,
        openItem = openList,
        onItemOpened = onItemOpened,
        onItemClosed = onItemClosed,
        onRemove = onRemove,
        onRename = onRename,
        onCopy = onCopy,
    ) { swipeOffset ->
        Card(
            modifier = Modifier
                .offset { IntOffset(swipeOffset.toInt(), 0) }
                .padding(horizontal = dimensionResource(R.dimen.padding_8x))
                .padding(vertical = dimensionResource(R.dimen.padding_4x))
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable {
                    onItemClick()
                },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(1.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
                    .padding(dimensionResource(R.dimen.padding_4x)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemIcon(
                    icon = item.iconResId,
                    onClick = onIconClick
                )
                Text(
                    text = item.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_4x))
                )
            }
        }
    }
}