package com.practicum.shoppinglist.details.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBottomSheet(
    bottomSheetState: SheetState,
    expanded: MutableState<Boolean>,
    selectedOption: MutableState<String>,
    onDismissRequest: () -> Unit,
    hideBottomSheet: () -> Unit,
    onSortClick: () -> Unit,
    onRemoveAll: () -> Unit,
    onClearClick: () -> Unit,
) {
    val anchorBounds = remember { mutableStateOf<Rect?>(null) }

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
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_3x))
    ) {
        Column(
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_4x))
                .fillMaxWidth()

        ) {
            if (expanded.value) {
                anchorBounds.value?.let { bounds ->
                    ActionMenu(
                        expanded = expanded,
                        selectedOption = selectedOption,
                        anchorBounds = bounds
                    )
                }
            }
            MenuItem(
                icon = R.drawable.ic_sort,
                text = stringResource(R.string.sort),
                subText = selectedOption.value,
                onClick = {
                    onSortClick()
                },
            )
            MenuItem(
                icon = R.drawable.ic_delete,
                text = stringResource(R.string.remove_all),
                onBounds = { bounds ->
                    anchorBounds.value = bounds
                },
                onClick = {
                    onRemoveAll()
                    hideBottomSheet()
                },
            )
            MenuItem(
                icon = R.drawable.ic_clear,
                text = stringResource(R.string.clear),
                onClick = {
                    onClearClick()
                    hideBottomSheet()
                },
            )
        }
    }
}