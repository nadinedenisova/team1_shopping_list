package com.practicum.shoppinglist.details.presentation.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
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
    modifier: Modifier = Modifier,
    shoppingListId: Long,
    fabViewModel: FabViewModel,
    onNavigateUp: () -> Unit = {},
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

    Scaffold(
        topBar = {
            if (fabState.isOpenDetailsBottomSheetState != null) {
                val height = WindowInsets.statusBars.asPaddingValues()

                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(height.calculateTopPadding() + 64.dp)
                        .background(Color.Black.copy(alpha = .32f))
                        .clickable(
                            interactionSource = null,
                            indication = null,
                        ) {
                            fabViewModel.onIntent(FabIntent.CloseDetailsBottomSheet)
                            keyboardController?.hide()
                        }
                        .zIndex(Float.MAX_VALUE)
                )
            }

            TopBar(
                onMenuClick = { showMenuBottomSheet.value = true },
                onBackClick = { onNavigateUp() }
            )
        },
        floatingActionButton = {
            val offset by animateDpAsState(
                targetValue = -fabState.offsetY,
            )

            val icon =
                if (fabState.isOpenDetailsBottomSheetState != null) Icons.Default.Done else Icons.Default.Add

            FloatingActionButton(
                modifier = Modifier.offset(y = offset),
                onClick = {
                    when (fabState.isOpenDetailsBottomSheetState) {
                        FabState.State.AddProduct.name -> fabViewModel.onIntent(
                            FabIntent.AddProduct(true)
                        )

                        FabState.State.EditProduct.name -> fabViewModel.onIntent(
                            FabIntent.EditProduct(true)
                        )

                        else -> fabViewModel.onIntent(
                            FabIntent.OpenDetailsBottomSheet(
                                state = FabState.State.AddProduct.name
                            )
                        )
                    }
                },
                shape = MaterialTheme.shapes.small,

                ) {
                Icon(icon, contentDescription = stringResource(R.string.add))
            }
        },
    ) { innerPadding ->
        DetailsScreenUI(
            modifier = modifier.padding(innerPadding),
            state = state,
            onIntent = { intent -> viewModel.onIntent(intent) },
            fabState = fabState,
            onFabIntent = { intent -> fabViewModel.onIntent(intent) },
            action = viewModel.action,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onMenuClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(stringResource(R.string.products_screen_title))
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        actions = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null
                )
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenUI(
    modifier: Modifier = Modifier,
    state: DetailsScreenState = DetailsScreenState(),
    onFabIntent: (FabIntent) -> Unit = {},
    fabState: FabState,
    onIntent: (DetailsScreenIntent) -> Unit = {},
    action: SharedFlow<ListAction>,
) {
    val openProduct = remember { mutableStateOf<ProductItem?>(null) }
    val density = LocalDensity.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            initialValue = if (fabState.isOpenDetailsBottomSheetState != null) SheetValue.Expanded else SheetValue.Hidden,
            confirmValueChange = { sheetValue ->
                onFabIntent(FabIntent.CloseDetailsBottomSheet)
                true
            },
            skipPartiallyExpanded = false,
            skipHiddenState = false,
            density = density
        )
    )

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
        scaffoldState = scaffoldState,
        sheetSwipeEnabled = true,
        sheetShape = SLTheme.shapes.large.copy(
            bottomEnd = CornerSize(0),
            bottomStart = CornerSize(0)
        ),
        sheetContent = {
            Column(
                modifier = Modifier
                    .onGloballyPositioned { layout ->
                        with(density) {
                            if (scaffoldState.bottomSheetState.isVisible) {
                                onFabIntent(FabIntent.OffsetY(layout.size.height.toDp() + 64.dp))
                            } else {
                                onFabIntent(FabIntent.OffsetY(0.dp))
                            }
                        }
                    }
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
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
                        modifier = Modifier,
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
                                    onIntent(DetailsScreenIntent.EditUnitsCount(it.trim().take(9).toInt()))
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
                            onValueChanged = { value -> onIntent(DetailsScreenIntent.EditUnit(value)) },
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
        }
    ) { innerPadding ->
        if (fabState.isOpenDetailsBottomSheetState != null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = .32f))
                    .clickable(
                        interactionSource = null,
                        indication = null,
                    ) {
                        onFabIntent(FabIntent.CloseDetailsBottomSheet)
                        keyboardController?.hide()
                    }
                    .zIndex(Float.MAX_VALUE)
            )
        }

        if (state.productList.isEmpty()) {
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(horizontal = 40.dp),
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
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .then(
                        if (state.sortOrderMode is ProductSortOrder.Manual) {
                            Modifier.dragContainer(dragDropState)
                        } else {
                            Modifier
                        }
                    ),
                state = lazyListState,
            ) {
                itemsIndexed(state.productList, key = { _, item -> item }) { index, item ->
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
                                    if (openProduct.value?.id == item.id) openProduct.value = null
                                },
                                onRename = {
                                    onFabIntent(FabIntent.OpenDetailsBottomSheet(state = FabState.State.EditProduct.name))
                                    onIntent(DetailsScreenIntent.SelectedProduct(item))
                                },
                                onRemove = { onIntent(BaseIntent.QueryRemoveShoppingList) },
                                action = action,
                                openItem = openProduct as MutableState<BaseItem?>,
                                onIntent = { intent -> onIntent(intent) },
                            )
                        }
                    }
                }
            }
        }
    }
}

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
