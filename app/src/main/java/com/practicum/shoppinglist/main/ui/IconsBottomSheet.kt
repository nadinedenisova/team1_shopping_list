package com.practicum.shoppinglist.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.main.ui.recycler.ItemIcon

val icons = listOf(
    R.drawable.ic_list, R.drawable.ic_gift, R.drawable.ic_products, R.drawable.ic_home,
    R.drawable.ic_florist, R.drawable.ic_construction, R.drawable.ic_celebration, R.drawable.ic_cake,
    R.drawable.ic_liquor, R.drawable.ic_pets, R.drawable.ic_gate, R.drawable.ic_medication,
    R.drawable.ic_education, R.drawable.ic_ski, R.drawable.ic_bar, R.drawable.ic_child_care,
    R.drawable.ic_game, R.drawable.ic_palette, R.drawable.ic_apparel, R.drawable.ic_car,
    R.drawable.ic_luggage, R.drawable.ic_medication, R.drawable.ic_self_care, R.drawable.ic_photo,
    R.drawable.ic_contacts, R.drawable.ic_self_improvement, R.drawable.ic_toys, R.drawable.ic_study,
    R.drawable.ic_childrens, R.drawable.ic_shopping,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconsBottomSheet(
    bottomSheetState: SheetState,
    onDismissRequest: () -> Unit,
    hideBottomSheet: () -> Unit,
    onIconClick: (Int) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        dragHandle = {
            Box(
                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_8x))
                    .width(32.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(100))
                    .background(MaterialTheme.colorScheme.outline)
            )
        },
        shape = RoundedCornerShape(
            topStart = dimensionResource(R.dimen.bottom_sheet_corner_radius),
            topEnd = dimensionResource(R.dimen.bottom_sheet_corner_radius)
        ),
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_3x))
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(48.dp),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_10x)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_10x))
            ) {
                items(icons) { item ->
                    ItemIcon(
                        icon = item,
                        onClick = {
                            onIconClick(item)
                            hideBottomSheet()
                        }
                    )
                }
            }
        }
    }
}