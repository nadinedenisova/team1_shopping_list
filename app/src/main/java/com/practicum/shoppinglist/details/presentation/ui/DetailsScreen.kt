package com.practicum.shoppinglist.details.presentation.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.practicum.shoppinglist.App
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.common.resources.DetailsScreenIntent
import com.practicum.shoppinglist.common.resources.ListAction
import com.practicum.shoppinglist.core.domain.models.BaseItem
import com.practicum.shoppinglist.core.domain.models.ProductItem
import com.practicum.shoppinglist.core.presentation.ui.FabViewModel
import com.practicum.shoppinglist.core.presentation.ui.components.SLDropdown
import com.practicum.shoppinglist.core.presentation.ui.components.SLIconButton
import com.practicum.shoppinglist.core.presentation.ui.components.SLInfo
import com.practicum.shoppinglist.core.presentation.ui.components.SLOutlineTextField
import com.practicum.shoppinglist.core.presentation.ui.dragDrop.DraggableItem
import com.practicum.shoppinglist.core.presentation.ui.dragDrop.dragContainer
import com.practicum.shoppinglist.core.presentation.ui.dragDrop.rememberDragDropState
import com.practicum.shoppinglist.core.presentation.ui.state.FabIntent
import com.practicum.shoppinglist.core.presentation.ui.state.FabState
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import com.practicum.shoppinglist.details.presentation.state.DetailsScreenState
import com.practicum.shoppinglist.details.presentation.viewmodel.DetailsViewModel
import com.practicum.shoppinglist.details.utils.model.ProductSortOrder
import com.practicum.shoppinglist.di.api.daggerViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    shoppingListId: Long,
    fabViewModel: FabViewModel,
    onNavigateToMainScreen: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    val factory = remember {
        (context.applicationContext as App).appComponent.viewModelFactory()
    }
    val viewModel = daggerViewModel<DetailsViewModel>(factory)

    viewModel.onIntent(DetailsScreenIntent.Init(shoppingListId))
    val state = viewModel.state.collectAsState().value
    val fabState = fabViewModel.fabState.collectAsState().value
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val expanded = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val menuSortOptions = remember {
        listOf(
            ProductSortOrder.ASC,
            ProductSortOrder.Manual,
        )
    }
    val showMenuBottomSheet = rememberSaveable { mutableStateOf(false) }
    val showBottomSheet = rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )

    LaunchedEffect(showBottomSheet.value) {
        scope.launch {
            if (showBottomSheet.value) {
                scaffoldState.bottomSheetState.show()
            } else {
                scaffoldState.bottomSheetState.hide()
                fabViewModel.onIntent(FabIntent.CloseDetailsBottomSheet)
            }
        }
    }

    LaunchedEffect(fabState.editProduct) {
        if (fabState.editProduct) {
            viewModel.onIntent(DetailsScreenIntent.EditProduct)
            fabViewModel.onIntent(FabIntent.EditProduct(false))
            fabViewModel.onIntent(FabIntent.CloseDetailsBottomSheet)
            keyboardController?.hide()
        }
    }

    LaunchedEffect(fabState.addProduct) {
        if (fabState.addProduct) {
            viewModel.onIntent(DetailsScreenIntent.AddProduct)
            fabViewModel.onIntent(FabIntent.AddProduct(false))
            fabViewModel.onIntent(FabIntent.CloseDetailsBottomSheet)
            keyboardController?.hide()
        }
    }

    if (showMenuBottomSheet.value) {
        MenuBottomSheet(
            bottomSheetState = bottomSheetState,
            expanded = expanded,
            options = menuSortOptions,
            selectedOption = state.sortOrderMode,
            onSelectionChanged = { viewModel.onIntent(DetailsScreenIntent.SortOrder(sortOrder = it)) },
            onDismissRequest = { showMenuBottomSheet.value = false },
            hideBottomSheet = {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        showMenuBottomSheet.value = false
                    }
                }
            },
            onSortClick = { expanded.value = true },
            onRemoveAll = { viewModel.onIntent(DetailsScreenIntent.DeleteAll) },
            onClearClick = { viewModel.onIntent(DetailsScreenIntent.DeteleCompleted) },
        )
    }

    DetailsScreenUI(
        state = state,
        onIntent = { intent -> viewModel.onIntent(intent) },
        fabState = fabState,
        onFabIntent = { intent -> fabViewModel.onIntent(intent) },
        action = viewModel.action,
        showMenuBottomSheet = showMenuBottomSheet,
        showBottomSheet = showBottomSheet,
        onNavigateToMainScreen = onNavigateToMainScreen,
        scaffoldState = scaffoldState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenUI(
    state: DetailsScreenState = DetailsScreenState(),
    onFabIntent: (FabIntent) -> Unit = {},
    fabState: FabState,
    onIntent: (DetailsScreenIntent) -> Unit = {},
    action: SharedFlow<ListAction>,
    showMenuBottomSheet: MutableState<Boolean>,
    showBottomSheet: MutableState<Boolean>,
    onNavigateToMainScreen: (() -> Unit)? = null,
    scaffoldState: BottomSheetScaffoldState,
) {
    val density = LocalDensity.current
    val openProduct = remember { mutableStateOf<BaseItem?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(fabState.isOpenDetailsBottomSheetState) {
        if (fabState.isOpenDetailsBottomSheetState == FabState.State.AddProduct.name) {
            onIntent(DetailsScreenIntent.SelectedProduct(ProductItem()))
        }
    }

    val lazyListState = rememberLazyListState()

    val dragDropState = rememberDragDropState(lazyListState) { fromIndex, toIndex ->
        onIntent(DetailsScreenIntent.UpdateManualSortOrder(fromIndex, toIndex))
    }

    BottomSheetScaffold(
        topBar = {
            TopBarDetailsScreen(
                onMenuClick = { showMenuBottomSheet.value = true },
                onBackClick = onNavigateToMainScreen
            )
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 240.dp,
        sheetSwipeEnabled = true,
        sheetDragHandle = {
        },
        sheetShape = RectangleShape,
        sheetContainerColor = Color.Transparent,
        sheetShadowElevation = 0.dp,
        sheetContent = {
            Surface(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_3x))
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .onGloballyPositioned { layout ->
                        with(density) {
                            if (showBottomSheet.value) {
                                onFabIntent(FabIntent.OffsetY(layout.size.height.toDp() + 8.dp))
                            } else {
                                onFabIntent(FabIntent.OffsetY(0.dp))
                            }
                        }
                    },
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shape = SLTheme.shapes.large.copy(
                    bottomEnd = CornerSize(0),
                    bottomStart = CornerSize(0)
                )
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_8x)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_8x))
                            .width(32.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(100))
                            .background(MaterialTheme.colorScheme.outline),
                    )
                    SLDropdown(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.product_title),
                        value = state.product.name,
                        onValueChanged = { value ->
                            onIntent(DetailsScreenIntent.EditName(value))
                        },
                        placeholder = stringResource(R.string.add_new_product_title),
                        editable = true,
                        menuItems = state.productMenuList
                    )
                    Spacer(Modifier.height(24.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val count = when {
                            state.product.count == 0 -> ""
                            else -> state.product.count.toString()
                        }

                        SLOutlineTextField(
                            modifier = Modifier.weight(1f),
                            value = count,
                            onValueChange = {
                                if (it.isNotEmpty() && it.trim().isDigitsOnly()) {
                                    onIntent(
                                        DetailsScreenIntent.EditUnitsCount(
                                            it.trim().take(9).toInt()
                                        )
                                    )
                                } else {
                                    onIntent(DetailsScreenIntent.EditUnitsCount(0))
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
                            menuItems = stringArrayResource(R.array.units).toList(),
                            value = state.product.unit,
                            showTrailingIcon = true,
                            onValueChanged = { value ->
                                onIntent(
                                    DetailsScreenIntent.EditUnit(
                                        value
                                    )
                                )
                            },
                            placeholder = stringResource(R.string.add_new_product_title),
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
                    Spacer(Modifier.height(24.dp))
                }
            }
        },
    ) { _ ->
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (state.productList.isEmpty()) {
                SLInfo(
                    modifier= Modifier
                        .padding(horizontal = 40.dp)
                        .fillMaxSize(),
                    image = SLTheme.images.noProductList,
                    title = stringResource(R.string.no_product_lists_title),
                    message = stringResource(R.string.no_product_lists_message),
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .then(
                            if (state.sortOrderMode is ProductSortOrder.Manual) {
                                Modifier.dragContainer(dragDropState)
                            } else {
                                Modifier
                            }
                        )
                        .fillMaxSize(),
                    state = lazyListState,
                ) {
                    itemsIndexed(items = state.productList, key = { _, item -> item }) { index, item ->
                        DraggableItem(dragDropState, index) { isDragging ->
                            val color by animateColorAsState(
                                if (isDragging) SLTheme.slColorScheme.materialScheme.onSurface.copy(
                                    alpha = 0.16f
                                ) else SLTheme.slColorScheme.materialScheme.surface
                            )
                            val elevation by animateDpAsState(
                                if (isDragging) SLTheme.elevation.level5
                                else SLTheme.elevation.level0
                            )
                            Surface(
                                shape = RectangleShape,
                                color = color.compositeOver(SLTheme.slColorScheme.materialScheme.surface),
                                shadowElevation = elevation
                            ) {
                                ProductItem(
                                    modifier = Modifier.background(color.compositeOver(SLTheme.slColorScheme.materialScheme.surface)),
                                    item = item,
                                    onCheckedChange = {
                                        onIntent(
                                            DetailsScreenIntent.ToggleCompleted(
                                                item
                                            )
                                        )
                                    },
                                    manualSort = state.sortOrderMode is ProductSortOrder.Manual,
                                    onItemClick = {
                                        openProduct.value = null
                                    },
                                    onItemOpened = {
                                        openProduct.value = item

                                    },
                                    onItemClosed = {
                                        if (openProduct.value?.id == item.id) openProduct.value =
                                            null
                                    },
                                    onRename = {
                                        onFabIntent(FabIntent.OpenDetailsBottomSheet(state = FabState.State.EditProduct.name))
                                        onIntent(DetailsScreenIntent.SelectedProduct(item))
                                        showBottomSheet.value = true
                                    },
                                    onRemove = { onIntent(BaseIntent.QueryRemoveShoppingList) },
                                    action = action,
                                    openItem = openProduct,
                                    onIntent = { intent -> onIntent(intent) },
                                )
                            }
                        }
                    }
                }
            }

            if (showBottomSheet.value) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = .32f))
                        .clickable(
                            interactionSource = null,
                            indication = null,
                        ) {
                            showBottomSheet.value = false
                            onFabIntent(FabIntent.CloseDetailsBottomSheet)
                            keyboardController?.hide()
                        }
                )
            }

            val offset by animateDpAsState(
                targetValue = -fabState.offsetY,
            )
            val icon = if (showBottomSheet.value) Icons.Default.Done else Icons.Default.Add

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = offset)
                    .padding(end = dimensionResource(R.dimen.padding_8x))
                    .padding(bottom = dimensionResource(R.dimen.padding_8x))
                    .navigationBarsPadding(),
                onClick = {
                    when (fabState.isOpenDetailsBottomSheetState) {
                        FabState.State.AddProduct.name -> onFabIntent(
                            FabIntent.AddProduct(true)
                        )

                        FabState.State.EditProduct.name -> onFabIntent(
                            FabIntent.EditProduct(true)
                        )

                        else -> onFabIntent(
                            FabIntent.OpenDetailsBottomSheet(
                                state = FabState.State.AddProduct.name
                            )
                        )
                    }

                    showBottomSheet.value = !showBottomSheet.value
                },
                shape = MaterialTheme.shapes.small,
            ) {
                Icon(icon, contentDescription = stringResource(R.string.add))
            }
        }
    }
}