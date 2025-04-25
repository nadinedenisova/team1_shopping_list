package com.practicum.shoppinglist.main.ui.recycler

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.ShoppingListIntent
import com.practicum.shoppinglist.core.domain.models.ListItem
import com.practicum.shoppinglist.main.ui.view_model.MainScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun ItemList(
    viewModel: MainScreenViewModel,
    list: ListItem,
    openList: ListItem?,
    onItemClick: () -> Unit,
    onIconClick: () -> Unit,
    onItemOpened: (ListItem) -> Unit,
    onItemClosed: () -> Unit,
    onRename: () -> Unit,
    onCopy: () -> Unit,
    onStartRemove: () -> Unit,
    onFinishRemove: () -> Unit,
) {
    val state by viewModel.shoppingListStateFlow.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val buttonsSwipe = with(LocalDensity.current) { (screenWidth * 0.35f).toPx() }
    val centerRemoveSwipe = with(LocalDensity.current) { (screenWidth * 0.5f).toPx() }
    val maxSwipe = with(LocalDensity.current) { (screenWidth * 0.9f).toPx() }
    val swipeOffset = remember { Animatable(0f) }
    val visibleState = remember { MutableTransitionState(true) }

    LaunchedEffect(openList) {
        if (openList?.id != list.id && swipeOffset.value != 0f) {
            swipeOffset.animateTo(0f)
        }
    }

    LaunchedEffect(visibleState.currentState, visibleState.targetState) {
        if (!visibleState.currentState && !visibleState.targetState) {
            onFinishRemove()
        }
    }

    LaunchedEffect(state.isRemoving) {
        if (openList?.id == list.id && state.isRemoving) {

            viewModel.processIntent(ShoppingListIntent.IsRemoving(false))
            visibleState.targetState = false
            scope.launch { swipeOffset.animateTo(0f) }
            onItemClosed()
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .pointerInput(openList?.id) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            val newOffset = (swipeOffset.value + dragAmount).coerceIn(-maxSwipe, 0f)
                            scope.launch {
                                swipeOffset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                if (swipeOffset.value <= -maxSwipe + 20f) {
                                    onStartRemove()
                                } else if (swipeOffset.value < -buttonsSwipe / 2) {
                                    swipeOffset.animateTo(-buttonsSwipe)
                                    onItemOpened(list)
                                } else {
                                    swipeOffset.animateTo(0f)
                                    onItemClosed()
                                }
                            }
                        }
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
            ) {
                val centerRemove = swipeOffset.value <= -centerRemoveSwipe
                if (centerRemove) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        ItemIcon(
                            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_2x)),
                            icon = R.drawable.ic_delete,
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.padding_8x))
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemIcon(
                            icon = R.drawable.ic_edit,
                            onClick = {
                                onRename()
                                scope.launch { swipeOffset.animateTo(0f) }
                                onItemClosed()
                            },
                        )
                        ItemIcon(
                            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_2x)),
                            icon = R.drawable.ic_copy,
                            onClick = {
                                onCopy()
                                scope.launch { swipeOffset.animateTo(0f) }
                                onItemClosed()
                            },
                        )
                        ItemIcon(
                            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_2x)),
                            icon = R.drawable.ic_delete,
                            onClick = onStartRemove
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .offset { IntOffset(swipeOffset.value.toInt(), 0) }
                    .padding(top = dimensionResource(R.dimen.padding_8x))
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onItemClick() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(1.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                        .padding(dimensionResource(R.dimen.padding_4x)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ItemIcon(
                        icon =  list.iconResId,
                        onClick = onIconClick
                    )
                    Text(
                        text = list.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_4x))
                    )
                }
            }
        }
    }
}

/*@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    icon: Int,
) {
    Box(
        modifier = modifier
            .size(dimensionResource(R.dimen.icon_size))
            .background(
                color = Color.Yellow,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            alignment = Alignment.Center,
            painter = painterResource(id = icon),
            contentDescription = null,
        )
    }
}*/