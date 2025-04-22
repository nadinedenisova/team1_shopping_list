package com.practicum.shoppinglist.details.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme

@Composable
fun DetailsScreen(
    showAddProductSheet: Boolean = false,
) {
    DetailsScreenUI(
        showAddProductSheet = showAddProductSheet,
    )
}

@Composable
private fun DetailsScreenUI(
    showAddProductSheet: Boolean = false,
) {
    Column(
        modifier = Modifier.padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(120.dp))
        Image(
            modifier = Modifier,
            painter = painterResource(R.drawable.no_product_list),
            contentDescription = null,
        )
        Spacer(Modifier.height(48.dp))
        Text(
            text = stringResource(R.string.no_product_lists_title),
            style = SLTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.no_product_lists_message),
            style = SLTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
        )
    }
}

@Preview(name = "Светлая тема", showSystemUi = true, device = PIXEL_6)
@Composable
private fun DetailsScreenUILightPreview() {
    SLTheme(darkTheme = false) {
        Scaffold(
            containerColor = SLTheme.slColorScheme.materialScheme.background
        ) {
            Surface(
                modifier = Modifier.padding(it),
            ) {
                DetailsScreenUI()
            }
        }
    }
}

@Preview(name = "Темная тема", showSystemUi = true, device = PIXEL_6)
@Composable
private fun DetailsScreenUIDarkPreview() {
    SLTheme(darkTheme = true) {
        Scaffold(
            containerColor = SLTheme.slColorScheme.materialScheme.background
        ) {
            Surface(
                modifier = Modifier.padding(it),
            ) {
                DetailsScreenUI()
            }
        }
    }
}