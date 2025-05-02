package com.practicum.shoppinglist.core.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.practicum.shoppinglist.R

@Composable
fun NoData(
    visible: Boolean,
    modifier: Modifier = Modifier,
    image: Int,
    title: String,
    message: String,
) {
    if (!visible) return

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            alignment = Alignment.Center,
            painter = painterResource(id = image),
            contentDescription = null,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_image)),
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_4x)),
        )
    }
}