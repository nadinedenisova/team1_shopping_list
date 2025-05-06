package com.practicum.shoppinglist.details.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.details.utils.model.ProductSortOrder

@Composable
fun ActionMenu(
    expanded: MutableState<Boolean>,
    selectedOption: ProductSortOrder,
    onSelectionChanged: (ProductSortOrder) -> Unit,
    options: List<ProductSortOrder>,
    anchorBounds: Rect
) {
    if (!expanded.value) return
    val density = LocalDensity.current
    val x = with(density) {
        (anchorBounds.right.toDp() - dimensionResource(R.dimen.sort_menu_width) + dimensionResource(
            R.dimen.padding_8x
        )).roundToPx()
    }
    val y =
        with(density) { (anchorBounds.top.toDp() - dimensionResource(R.dimen.padding_4x)).roundToPx() }

    Popup(
        alignment = Alignment.TopStart,
        offset = IntOffset(x, y),
        onDismissRequest = { expanded.value = false },
        properties = PopupProperties(focusable = true),
    ) {
        Surface(
            modifier = Modifier
                .width(dimensionResource(R.dimen.sort_menu_width))
                .shadow(8.dp, RoundedCornerShape(8.dp)),
            color = MaterialTheme.colorScheme.surfaceContainer,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                options.forEach {
                    PopupMenuItem(
                        expanded = expanded,
                        option = it,
                        selected = it == selectedOption,
                        onSelected = onSelectionChanged,
                    )
                }
            }
        }
    }
}