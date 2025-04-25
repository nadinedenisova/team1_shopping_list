package com.practicum.shoppinglist.details.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.text.isDigitsOnly
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.core.presentation.ui.components.SLDropdown
import com.practicum.shoppinglist.core.presentation.ui.components.SLIconButton
import com.practicum.shoppinglist.core.presentation.ui.components.SLOutlineTextField
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import com.practicum.shoppinglist.details.presentation.state.DetailsScreenIntent
import com.practicum.shoppinglist.details.presentation.state.DetailsScreenState
import com.practicum.shoppinglist.details.presentation.viewmodel.DetailsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    showMenuBottomSheet: MutableState<Boolean>,
    showAddProductListDialog: MutableState<Boolean>,
    viewModel: DetailsViewModel = remember { DetailsViewModel() },
    shoppingListId: Long,
) {
    val state = viewModel.state.collectAsState().value
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = rememberSaveable { mutableStateOf("") }

    if (showMenuBottomSheet.value) {
        MenuBottomSheet(
            bottomSheetState = bottomSheetState,
            expanded = expanded,
            selectedOption = selectedOption,
            onDismissRequest =  { showMenuBottomSheet.value = false },
            hideBottomSheet = {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        showMenuBottomSheet.value = false
                    }
                }
            },
            onSortClick = { expanded.value = true },
            onRemoveAll = {},
            onClearClick = {},
        )
    }

    DetailsScreenUI(
        modifier = modifier,
        state = state,
        onIntent = { intent -> viewModel.onIntent(intent) },
        showAddProductListDialog = showAddProductListDialog,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreenUI(
    modifier: Modifier = Modifier,
    state: DetailsScreenState = DetailsScreenState(),
    onIntent: (DetailsScreenIntent) -> Unit = {},
    showAddProductListDialog: MutableState<Boolean> = mutableStateOf(false),
) {
    val density = LocalDensity.current

    val sheetState = remember {
        SheetState(
            initialValue = SheetValue.Expanded,
            skipPartiallyExpanded = true,
            density = density,
        )
    }

    if (showAddProductListDialog.value) {
        ModalBottomSheet(
            onDismissRequest = {
                showAddProductListDialog.value = false
                onIntent(DetailsScreenIntent.CloseAddProductSheet)
            },
            shape = SLTheme.shapes.large.copy(bottomEnd = CornerSize(0), bottomStart = CornerSize(0)),
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                SLDropdown(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.product_title),
                    placeholder = stringResource(R.string.add_new_product_title),
                    editable = true,
                    menuItems = state.productList
                )
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SLOutlineTextField(
                        value = if (state.product.count == 0) "" else state.product.count.toString(),
                        onValueChange = {
                            if (it.isNotEmpty() && it.trim().isDigitsOnly()) {
                                onIntent(DetailsScreenIntent.EditUnits(it.trim().toInt()))
                            } else {
                                onIntent(DetailsScreenIntent.EditUnits(0))
                            }
                        },
                        label = stringResource(R.string.quantity_title),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    SLDropdown(
                        modifier = Modifier.weight(1f),
                        label = stringResource(R.string.units_title),
                        menuItems = state.unitList,
                        editable = false,
                        showTrailingIcon = true,
                    )
                    SLIconButton(
                        modifier = Modifier,
                        enabled = state.product.count > 0,
                        icon = painterResource(R.drawable.ic_minus),
                        onClick = { onIntent(DetailsScreenIntent.SubstractUnits) }
                    )
                    SLIconButton(
                        modifier = Modifier,
                        icon = painterResource(R.drawable.ic_plus),
                        onClick = { onIntent(DetailsScreenIntent.AddUnits) }
                    )
                }

            }
        }
    }

    Column(
        modifier = modifier.padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(120.dp))
        Image(
            modifier = Modifier,
            painter = painterResource(SLTheme.images.noProductList),
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

@Composable
fun ActionMenu(
    expanded: MutableState<Boolean>,
    selectedOption: MutableState<String>,
    anchorBounds: Rect
) {
    if (!expanded.value) return

    val context = LocalContext.current
    val density = LocalDensity.current
    val options = remember {
        mapOf(
            context.resources.getString(R.string.sort_alphabet_order) to R.drawable.ic_alphabet_order,
            context.resources.getString(R.string.sort_user_defined) to R.drawable.ic_user_defined,
        )
    }
    val x = with(density) { (anchorBounds.right.toDp() - dimensionResource(R.dimen.sort_menu_width) + dimensionResource(R.dimen.padding_8x)).roundToPx() }
    val y = with(density) { (anchorBounds.top.toDp() - dimensionResource(R.dimen.padding_4x)).roundToPx() }

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
                options.forEach { option ->
                    PopupMenuItem(
                        leadingIcon = option.value,
                        expanded = expanded,
                        option = option.key,
                        selectedOption = selectedOption,
                    )
                }
            }
        }
    }

    /*DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        offset = DpOffset(x, y),
        properties = PopupProperties(focusable = true),
        modifier = Modifier
            .width(dimensionResource(R.dimen.sort_menu_width))
            .onGloballyPositioned { coordinates ->
                menuHeight = coordinates.size.height
            }
    ) {
        options.forEach { option ->
            PopupMenuItem(
                leadingIcon = option.value,
                expanded = expanded,
                option = option.key,
                selectedOption = selectedOption,
            )
        }
    }*/
}

@Composable
fun PopupMenuItem(
    leadingIcon: Int,
    expanded: MutableState<Boolean>,
    option: String,
    selectedOption: MutableState<String>,
) {
    Row(
        modifier = Modifier
            .clickable {
                selectedOption.value = option
                expanded.value = false
            }
            .padding(start = dimensionResource(R.dimen.padding_6x), end = dimensionResource(R.dimen.padding_8x))
            .padding(vertical = dimensionResource(R.dimen.padding_8x))
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = leadingIcon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_6x)))
        Text(
            text = option,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_6x)))
        RadioButton(
            selected = option == selectedOption.value,
            onClick = null
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
                DetailsScreenUI(
                    showAddProductListDialog = remember { mutableStateOf(true) }
                )
            }
        }
    }
}

//@Preview(name = "Темная тема", showSystemUi = true, device = PIXEL_6)
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