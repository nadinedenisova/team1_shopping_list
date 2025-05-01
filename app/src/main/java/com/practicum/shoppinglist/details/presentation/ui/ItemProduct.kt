package com.practicum.shoppinglist.details.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.common.resources.ListAction
import com.practicum.shoppinglist.core.domain.models.BaseItem
import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.core.presentation.ui.components.SLCheckbox
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import com.practicum.shoppinglist.details.presentation.models.ProductItemUiModel
import com.practicum.shoppinglist.main.ui.recycler.SwipeItem
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ItemProduct(
    onIntent: (BaseIntent) -> Unit,
    action: SharedFlow<ListAction>,
    item: ProductItem,
    openItem: MutableState<BaseItem?>,
    onCheckedChange: (Boolean) -> Unit = {},
    manualSort: Boolean = false,
    onItemClick: () -> Unit,
    onItemOpened: () -> Unit,
    onItemClosed: () -> Unit,
    onRemove: () -> Unit,
    onRename: () -> Unit,
) {
    SwipeItem(
        onIntent = onIntent,
        action = action,
        item = item,
        openItem = openItem,
        onItemOpened = onItemOpened,
        onItemClosed = onItemClosed,
        onRemove = onRemove,
        onRename = onRename,
        extraPadding = true,
    ) { swipeOffset ->

        val textDecoration = if (item.completed) {
            TextDecoration.LineThrough
        } else {
            TextDecoration.None
        }

        Column(
            modifier = Modifier
                .offset { IntOffset(swipeOffset.toInt(), 0) }
                .background(MaterialTheme.colorScheme.surface)
                .height(72.dp)
                .clickable { onItemClick() },
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SLCheckbox(
                    modifier = Modifier,
                    checked = item.completed,
                    onCheckedChange = onCheckedChange,
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.name,
                        style = SLTheme.typography.bodyLarge.copy(
                            textDecoration = textDecoration
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = SLTheme.slColorScheme.materialScheme.onSurface,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.count.toString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = SLTheme.typography.bodyMedium,
                        color = SLTheme.slColorScheme.materialScheme.onSurfaceVariant,
                    )
                }
                if (manualSort) {
                    Icon(
                        painter = painterResource(R.drawable.drag_handle),
                        contentDescription = null,
                        tint = SLTheme.slColorScheme.materialScheme.onSurfaceVariant,
                    )
                }
                Spacer(Modifier.width(16.dp))
            }
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(SLTheme.slColorScheme.materialScheme.outlineVariant)
            )
        }
    }
}

private val productMock = listOf(
    ProductItemUiModel(
        id = 1, name = "Яблоки", amount = "1 кг", completed = false
    ),
    ProductItemUiModel(
        id = 2, name = "Бананы", amount = "3 шт", completed = true
    ),
    ProductItemUiModel(
        id = 3, name = "Молоко", amount = "2 л", completed = false,
    ),
)

@Preview(device = PIXEL_6)
@Composable
fun ProductItemUiPreviewLight() {
    SLTheme(darkTheme = false) {
        Scaffold {
            Column(modifier = Modifier.padding(it)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(productMock.size) {
                        //ProductItem(item = productMock[it], manualSort = it % 2 == 0)
                    }
                }
            }
        }
    }
}