package com.practicum.shoppinglist.details.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.details.utils.model.ProductSortOrder

@Composable
fun PopupMenuItem(
    expanded: MutableState<Boolean>,
    option: ProductSortOrder,
    selected: Boolean = false,
    onSelected: (ProductSortOrder) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable {
                expanded.value = false
                onSelected(option)
            }
            .padding(
                start = dimensionResource(R.dimen.padding_6x),
                end = dimensionResource(R.dimen.padding_8x)
            )
            .padding(vertical = dimensionResource(R.dimen.padding_8x))
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(option.leadingIcon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_6x)))
        Text(
            text = stringResource(option.name),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_6x)))
        RadioButton(
            selected = selected,
            onClick = null
        )
    }
}