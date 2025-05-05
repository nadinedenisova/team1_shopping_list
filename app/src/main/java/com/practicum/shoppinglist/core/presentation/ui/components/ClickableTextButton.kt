package com.practicum.shoppinglist.core.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ClickableTextButton(text: String, onClick: () -> Unit = {},) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextButton(
            onClick = onClick,
        ) {
            Text(text = text)
        }
    }
}
