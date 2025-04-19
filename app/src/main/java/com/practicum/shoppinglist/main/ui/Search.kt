package com.practicum.shoppinglist.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.R

@Composable
fun Search(
    visible: Boolean,
    searchQuery: MutableState<String>,
    onBackClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onBtnClearClick: () -> Unit,
) {
    if (!visible) return

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(color = MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_8x))
                    .size(24.dp)
                    .clickable { onBackClick() },
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
            )
            SearchTextField(
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_4x))
                    .weight(1f),
                searchQuery = searchQuery,
                onValueChange = onValueChange,
            )
            ClearBtn(visible = searchQuery.value.isNotEmpty(), onClick = onBtnClearClick)
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.BottomStart)
                .background(Color.Black)
        )
    }
}

@Composable
private fun SearchTextField(
    modifier: Modifier,
    searchQuery: MutableState<String>,
    onValueChange: (String) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        if (searchQuery.value.isEmpty()) {
            Text(
                text = stringResource(R.string.search_hint),
            )
        }

        val textSelectionColors = TextSelectionColors(
            handleColor = Color.Blue,
            backgroundColor = Color.Blue
        )
        CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                value = searchQuery.value,
                onValueChange = { newValue: String ->
                    onValueChange(newValue)
                },
                textStyle = TextStyle(
                    color = colorResource(R.color.black),
                    textAlign = TextAlign.Start
                ),
                cursorBrush = SolidColor(Color.Blue),
            )
        }
    }
}

@Composable
private fun ClearBtn(visible: Boolean, onClick: () -> Unit) {
    if (!visible) return

    Image(
        modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_4x))
            .clickable {
                onClick()
            },
        painter = painterResource(id = R.drawable.ic_close),
        contentDescription = null,
    )
}