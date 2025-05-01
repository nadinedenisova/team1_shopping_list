package com.practicum.shoppinglist.main.ui.recycler

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.practicum.shoppinglist.R
import com.practicum.shoppinglist.common.resources.BaseIntent
import com.practicum.shoppinglist.common.resources.ListAction
import com.practicum.shoppinglist.core.domain.models.BaseItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun SwipeItem(
    onIntent: (BaseIntent) -> Unit,
    action: SharedFlow<ListAction>,
    item: BaseItem,
    openItem: MutableState<BaseItem?>,
    onItemOpened: (BaseItem) -> Unit,
    onItemClosed: () -> Unit,
    onRemove: (() -> Unit)? = null,
    onRename: (() -> Unit)? = null,
    onCopy: (() -> Unit)? = null,
    content: @Composable (Float) -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var buttonsSwipe by remember { mutableFloatStateOf(0f) }
    val centerRemoveSwipe = with(LocalDensity.current) { (screenWidth * 0.5f).toPx() }
    val maxSwipe = with(LocalDensity.current) { (screenWidth * 0.9f).toPx() }
    val swipeOffset = remember { Animatable(0f) }
    val visibleState = remember { MutableTransitionState(true) }

    LaunchedEffect(Unit) {
        val buttonsCount = listOf(onRemove, onRename, onCopy).count { it != null }
        buttonsSwipe = context.resources.getDimension(R.dimen.padding_10x) +
            context.resources.getDimension(R.dimen.icon_size) * buttonsCount +
            context.resources.getDimension(R.dimen.padding_4x) * (buttonsCount - 1)
    }

    LaunchedEffect(openItem.value) {
        if (openItem.value?.id != item.id && swipeOffset.value != 0f) {
            swipeOffset.animateTo(0f)
        }
    }

    LaunchedEffect(visibleState.currentState, visibleState.targetState) {
        if (!visibleState.currentState && !visibleState.targetState) {

            onIntent(BaseIntent.RemoveListItem(item.id))
        }
    }

    LaunchedEffect(Unit) {
        action.collect { action ->
            when {
                action is ListAction.RemoveItem && openItem.value?.id == item.id -> {
                    visibleState.targetState = false
                    scope.launch { swipeOffset.animateTo(0f) }
                    onItemClosed()
                }
            }
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
                .pointerInput(openItem.value?.id) {
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
                                    onRemove?.let { it() }
                                } else if (swipeOffset.value < -buttonsSwipe / 2) {
                                    swipeOffset.animateTo(-buttonsSwipe)
                                    onItemOpened(item)
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
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(R.dimen.padding_10x))
                    .padding(top = dimensionResource(R.dimen.padding_8x))
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
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (onRename != null) {
                            ItemIcon(
                                icon = R.drawable.ic_edit,
                                onClick = {
                                    onRename()
                                    scope.launch { swipeOffset.animateTo(0f) }
                                    onItemClosed()
                                },
                            )
                        }
                        if (onCopy != null) {
                            ItemIcon(
                                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_2x)),
                                icon = R.drawable.ic_copy,
                                onClick = {
                                    onCopy()
                                    scope.launch { swipeOffset.animateTo(0f) }
                                    onItemClosed()
                                },
                            )
                        }
                        if (onRemove != null) {
                            ItemIcon(
                                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_2x)),
                                icon = R.drawable.ic_delete,
                                onClick = onRemove
                            )
                        }
                    }
                }
            }

            content(swipeOffset.value)
        }
    }
}