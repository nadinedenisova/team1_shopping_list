package com.practicum.shoppinglist.core.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme

@Composable
fun HorizontalCard(
    modifier: Modifier = Modifier,
    list: ListItem,
    onItemClick: () -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = SLTheme.elevation.level2
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(dimensionResource(R.dimen.padding_4x))
                .clickable { onItemClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            SLIconButton(
                icon = painterResource(list.iconResId),
            )
            Text(
                text = list.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = SLTheme.slColorScheme.materialScheme.onSurface,
                style = SLTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_4x))
            )
        }
    }
}

private val listItems = listOf (
    ListItem(1, "Продукты", R.drawable.ic_products),
    ListItem(2, "Для дома", R.drawable.ic_home),
    ListItem(3, "Подарки к Новому году", R.drawable.ic_gift),
)

@Preview(name = "Темная тема", showSystemUi = true, device = PIXEL_6)
@Composable
fun HorizontalCardPreviewDark() {
    SLTheme(darkTheme = true) {
        Scaffold(
            containerColor = SLTheme.slColorScheme.materialScheme.background
        ) {innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
            ) {
                items(listItems) {
                    Spacer(modifier = Modifier.size(16.dp))
                    HorizontalCard(list = it, modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}

@Preview(name = "Светлая тема", showSystemUi = true, device = PIXEL_6)
@Composable
fun HorizontalCardPreviewLight() {
    SLTheme(darkTheme = false) {
        Scaffold(
            containerColor = SLTheme.slColorScheme.materialScheme.background
        ) {innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
            ) {
                items(listItems) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalCard(list = it, modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}