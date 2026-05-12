@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.foundation.theme.YesTheme
import kotlin.math.absoluteValue

@Suppress("unused")
@Composable
fun ToggleSwitch(
	toggled: Boolean,
	modifier: Modifier = Modifier,
	onToggled: ((Boolean) -> Unit)? = null,
	enabled: Boolean = true,
	shape: Shape = YesTheme.shapes.pill,
	backgroundColor: Color = if (toggled) YesTheme.colors.tint else YesTheme.colors.divider,
	contentPadding: PaddingValues = PaddingValues(3.dp),
	interactionSource: MutableInteractionSource? = null,
	indication: Indication = LocalIndication.current,
	thumb: (@Composable BoxScope.(Color) -> Unit)? = null,
) {
	val colors = YesTheme.colors
	val currentOnToggled by rememberUpdatedState(onToggled)
	val source = interactionSource ?: remember { MutableInteractionSource() }
	val isPressed by source.collectIsPressedAsState()
	val isDragged by source.collectIsDraggedAsState()
	val isHovered by source.collectIsHoveredAsState()
	val hapticFeedback = LocalHapticFeedback.current

	var rawDragOffset by remember { mutableFloatStateOf(0f) }
	var dragOffset by remember { mutableFloatStateOf(0f) }
	var currentDragInteraction by remember { mutableStateOf<DragInteraction.Start?>(null) }
	val hasCallback = onToggled != null
	val dragLimit = 15f
	val enabledInteractive = enabled && hasCallback
	val thumbOffset by animateDpAsState(
		targetValue = (if (toggled) 21.dp else 6.dp) + dragOffset.dp,
		animationSpec = spring(dampingRatio = 0.65f, stiffness = 800f),
		label = "ToggleSwitchThumbOffset",
	)
	val thumbScale by animateFloatAsState(
		targetValue = if (enabled && (isPressed || isDragged || isHovered)) 1.15f else 1f,
		animationSpec = spring(dampingRatio = 0.5f, stiffness = 800f),
		label = "ToggleSwitchThumbScale",
	)
	val thumbSize = if (toggled) 20.dp else 16.dp
	val thumbColor by animateColorAsState(
		targetValue = if (enabled) colors.onTint else colors.disabledBackground,
		label = "ToggleSwitchThumbColor",
	)
	val trackColor by animateColorAsState(
		targetValue = when {
			enabled -> backgroundColor
			toggled -> colors.disabledTint
			else    -> colors.disabledBackgroundVariant
		},
		animationSpec = spring(dampingRatio = 0.99f, stiffness = 438.6f),
		label = "ToggleSwitchTrackColor",
	)
	val toggleableModifier = if (hasCallback) Modifier.toggleable(
		value = toggled,
		enabled = enabled,
		role = Role.Switch,
		interactionSource = source,
		indication = indication,
		onValueChange = { value ->
			currentOnToggled?.invoke(value)
			hapticFeedback.performHapticFeedback(if (value) HapticFeedbackType.ToggleOn else HapticFeedbackType.ToggleOff)
		},
	)
	else Modifier

	Box(
		modifier.wrapContentSize().size(width = 45.dp, height = 28.dp).clip(shape).drawBehind { drawRect(trackColor) }
			.hoverable(source, enabled).then(toggleableModifier),
	) {
		Box(
			Modifier.size(thumbSize).align(androidx.compose.ui.Alignment.CenterStart)
				.offset { IntOffset(thumbOffset.roundToPx(), 0) }.graphicsLayer {
					scaleX = thumbScale
					scaleY = thumbScale
				}.drawBehind { drawCircle(thumbColor) }.then(
					if (enabledInteractive) Modifier.draggable(
						orientation = Orientation.Horizontal,
						state = rememberDraggableState { dragAmount ->
							rawDragOffset += dragAmount / 2f
							dragOffset = if (toggled) rawDragOffset.coerceIn(-dragLimit, 0f)
							else rawDragOffset.coerceIn(0f, dragLimit)

						},
						onDragStarted = {
							currentDragInteraction = DragInteraction.Start().also { source.tryEmit(it) }
							rawDragOffset = 0f
							dragOffset = 0f
						},
						onDragStopped = {
							if (dragOffset.absoluteValue > dragLimit / 2f) {
								val next = !toggled
								currentOnToggled?.invoke(next)
								hapticFeedback.performHapticFeedback(
									if (next) HapticFeedbackType.ToggleOn else HapticFeedbackType.ToggleOff,
								)
							}
							currentDragInteraction?.let { source.tryEmit(DragInteraction.Stop(it)) }
							currentDragInteraction = null
							rawDragOffset = 0f
							dragOffset = 0f
						},
					)
					else Modifier,
				),
		) { thumb?.invoke(this, thumbColor) }
	}
}