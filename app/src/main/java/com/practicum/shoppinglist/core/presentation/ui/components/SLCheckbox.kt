package com.practicum.shoppinglist.core.presentation.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Devices.PIXEL_6
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.practicum.shoppinglist.core.presentation.ui.theme.SLTheme
import kotlin.math.floor

@Composable
fun SLCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit = {},
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    CircleCheckBoxImpl(
        enabled = enabled,
        value = ToggleableState(checked),
        modifier = modifier
            .minimumInteractiveComponentSize()
            .triStateToggleable(
                state = ToggleableState(checked),
                onClick = { onCheckedChange(!checked) },
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = null,
                indication = ripple(
                    bounded = false,
                    radius = CheckboxSize,
                )
            )
            .padding(CheckboxDefaultPadding),
        colors = colors,
    )
}

@Composable
private fun CircleCheckBoxImpl(
    enabled: Boolean,
    value: ToggleableState,
    modifier: Modifier,
    colors: CheckboxColors
) {
    val transition = updateTransition(value)
    val checkDrawFraction =
        transition.animateFloat(
            transitionSpec = {
                when {
                    initialState == ToggleableState.Off -> tween(CheckAnimationDuration)
                    targetState == ToggleableState.Off -> snap(BoxOutDuration)
                    else -> spring()
                }
            }
        ) {
            when (it) {
                ToggleableState.On -> 1f
                ToggleableState.Off -> 0f
                ToggleableState.Indeterminate -> 1f
            }
        }

    val checkCenterGravitationShiftFraction =
        transition.animateFloat(
            transitionSpec = {
                when {
                    initialState == ToggleableState.Off -> snap()
                    targetState == ToggleableState.Off -> snap(BoxOutDuration)
                    else -> tween(durationMillis = CheckAnimationDuration)
                }
            }
        ) {
            when (it) {
                ToggleableState.On -> 0f
                ToggleableState.Off -> 0f
                ToggleableState.Indeterminate -> 1f
            }
        }
    val checkCache = remember { CheckDrawingCache() }
    val checkColor = colors.checkmarkColor(value)
    val boxColor = colors.boxColor(enabled, value)
    val borderColor = colors.borderColor(enabled, value)
    Canvas(
        modifier
            .wrapContentSize(Alignment.Center)
            .requiredSize(CheckboxSize)
    ) {
        val strokeWidthPx = floor(StrokeWidth.toPx())

        drawContainer(
            boxColor = boxColor.value,
            borderColor = borderColor.value,
            radius = size.width / 2,
            strokeWidth = strokeWidthPx
        )
        drawCheck(
            checkColor = checkColor.value,
            checkFraction = checkDrawFraction.value,
            crossCenterGravitation = checkCenterGravitationShiftFraction.value,
            strokeWidthPx = strokeWidthPx,
            drawingCache = checkCache
        )
    }
}

@Immutable
private class CheckDrawingCache(
    val checkPath: Path = Path(),
    val pathMeasure: PathMeasure = PathMeasure(),
    val pathToDraw: Path = Path()
)


private fun DrawScope.drawContainer(
    boxColor: Color,
    borderColor: Color,
    radius: Float,
    strokeWidth: Float
) {
    val halfStrokeWidth = strokeWidth / 2.0f
    val stroke = Stroke(strokeWidth)
    if (boxColor == borderColor) {
        drawCircle(
            boxColor,
            radius = radius,
            style = Fill
        )
    } else {
        drawCircle(
            boxColor,
            radius = radius,
            style = Fill
        )
        drawCircle(
            borderColor,
            radius = radius - halfStrokeWidth,
            style = stroke
        )
    }
}

private fun DrawScope.drawCheck(
    checkColor: Color,
    checkFraction: Float,
    crossCenterGravitation: Float,
    strokeWidthPx: Float,
    drawingCache: CheckDrawingCache,
) {
    val stroke = Stroke(width = strokeWidthPx, cap = StrokeCap.Square)
    val width = size.width
    val checkCrossX = 0.425f
    val checkCrossY = 0.675f
    val leftX = 0.275f
    val leftY = 0.525f
    val rightX = 0.725f
    val rightY = 0.375f

    val gravitatedCrossX = lerp(checkCrossX, 0.5f, crossCenterGravitation)
    val gravitatedCrossY = lerp(checkCrossY, 0.5f, crossCenterGravitation)
    // gravitate only Y for end to achieve center line
    val gravitatedLeftY = lerp(leftY, 0.5f, crossCenterGravitation)
    val gravitatedRightY = lerp(rightY, 0.5f, crossCenterGravitation)

    with(drawingCache) {
        checkPath.reset()
        checkPath.moveTo(width * leftX, width * gravitatedLeftY)
        checkPath.lineTo(width * gravitatedCrossX, width * gravitatedCrossY)
        checkPath.lineTo(width * rightX, width * gravitatedRightY)
        pathMeasure.setPath(checkPath, false)
        pathToDraw.reset()
        pathMeasure.getSegment(0f, pathMeasure.length * checkFraction, pathToDraw, true)
    }
    drawPath(drawingCache.pathToDraw, checkColor, style = stroke)
}

@Composable
private fun CheckboxColors.checkmarkColor(state: ToggleableState): State<Color> {
    val target =
        if (state == ToggleableState.Off) {
            uncheckedCheckmarkColor
        } else {
            checkedCheckmarkColor
        }

    val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
    return animateColorAsState(target, tween(durationMillis = duration))
}

/**
 * Represents the color used for the box (background) of the checkbox, depending on [enabled]
 * and [state].
 *
 * @param enabled whether the checkbox is enabled or not
 * @param state the [ToggleableState] of the checkbox
 */
@Composable
private fun CheckboxColors.boxColor(enabled: Boolean, state: ToggleableState): State<Color> {
    val target =
        if (enabled) {
            when (state) {
                ToggleableState.On,
                ToggleableState.Indeterminate -> checkedBoxColor

                ToggleableState.Off -> uncheckedBoxColor
            }
        } else {
            when (state) {
                ToggleableState.On -> disabledCheckedBoxColor
                ToggleableState.Indeterminate -> disabledIndeterminateBoxColor
                ToggleableState.Off -> disabledUncheckedBoxColor
            }
        }

    // If not enabled 'snap' to the disabled state, as there should be no animations between
    // enabled / disabled.
    return if (enabled) {
        val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
        animateColorAsState(target, tween(durationMillis = duration))
    } else {
        rememberUpdatedState(target)
    }
}

/**
 * Represents the color used for the border of the checkbox, depending on [enabled] and [state].
 *
 * @param enabled whether the checkbox is enabled or not
 * @param state the [ToggleableState] of the checkbox
 */
@Composable
private fun CheckboxColors.borderColor(enabled: Boolean, state: ToggleableState): State<Color> {
    val target =
        if (enabled) {
            when (state) {
                ToggleableState.On,
                ToggleableState.Indeterminate -> checkedBorderColor

                ToggleableState.Off -> uncheckedBorderColor
            }
        } else {
            when (state) {
                ToggleableState.Indeterminate -> disabledIndeterminateBorderColor
                ToggleableState.On -> disabledBorderColor
                ToggleableState.Off -> disabledUncheckedBorderColor
            }
        }

    // If not enabled 'snap' to the disabled state, as there should be no animations between
    // enabled / disabled.
    return if (enabled) {
        val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
        animateColorAsState(target, tween(durationMillis = duration))
    } else {
        rememberUpdatedState(target)
    }
}


private const val BoxInDuration = 50
private const val BoxOutDuration = 100
private const val CheckAnimationDuration = 100

private val CheckboxDefaultPadding = 2.dp
private val CheckboxSize = 20.dp
private val StrokeWidth = 2.dp

@Preview(device = PIXEL_6)
@Composable
fun SLCheckboxPreview() {
    SLTheme(
        darkTheme = false
    ) {
        Surface {
            Row(Modifier.padding(32.dp)) {
                var state by remember { mutableStateOf(false) }
                var state2 by remember { mutableStateOf(false) }
                var state3 by remember { mutableStateOf(false) }
                SLCheckbox(
                    checked = state,
                    onCheckedChange = { state = it },
                )
                SLCheckbox(
                    checked = state2,
                    onCheckedChange = { state2 = it },
                    enabled = false,
                )
                Checkbox(state3, {
                    state3 = it
                })
            }
        }
    }
}