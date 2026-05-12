@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.*
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.composeunstyled.ProvideContentColor
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun TriStateCheckBox(
	value: ToggleableState,
	modifier: Modifier = Modifier,
	size: Dp = 26.dp,
	backgroundColor: Color = when (value) {
		ToggleableState.On            -> YesTheme.colors.tint
		ToggleableState.Indeterminate -> YesTheme.colors.tint
		ToggleableState.Off           -> YesTheme.colors.divider
	},
	contentColor: Color = YesTheme.colors.onTint,
	enabled: Boolean = true,
	onClick: () -> Unit,
	shape: Shape = YesTheme.shapes.pill,
	borderColor: Color = Color.Unspecified,
	borderWidth: Dp = 0.dp,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	contentDescription: String? = null,
	checkIcon: (@Composable (ToggleableState) -> Unit)? = null,
) = AnimatedCheckBox(
	value = value,
	modifier = modifier,
	size = size,
	backgroundColor = backgroundColor,
	contentColor = contentColor,
	uncheckedBackgroundColor = YesTheme.colors.divider,
	disabledBackgroundColor = YesTheme.colors.disabledBackgroundVariant,
	disabledContentColor = YesTheme.colors.disabledBackground,
	enabled = enabled,
	onClick = onClick,
	shape = shape,
	borderColor = borderColor,
	borderWidth = borderWidth,
	interactionSource = interactionSource,
	indication = indication,
	contentDescription = contentDescription,
	checkIcon = checkIcon,
)

@Suppress("unused")
@Composable
fun TriStateCheckbox(
	value: ToggleableState,
	modifier: Modifier = Modifier,
	size: Dp = 26.dp,
	backgroundColor: Color = when (value) {
		ToggleableState.On            -> YesTheme.colors.tint
		ToggleableState.Indeterminate -> YesTheme.colors.tint
		ToggleableState.Off           -> YesTheme.colors.divider
	},
	contentColor: Color = YesTheme.colors.onTint,
	enabled: Boolean = true,
	onClick: () -> Unit,
	shape: Shape = YesTheme.shapes.pill,
	borderColor: Color = Color.Unspecified,
	borderWidth: Dp = 0.dp,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	contentDescription: String? = null,
	checkIcon: (@Composable (ToggleableState) -> Unit)? = null,
) = TriStateCheckBox(
	value = value,
	modifier = modifier,
	size = size,
	backgroundColor = backgroundColor,
	contentColor = contentColor,
	enabled = enabled,
	onClick = onClick,
	shape = shape,
	borderColor = borderColor,
	borderWidth = borderWidth,
	interactionSource = interactionSource,
	indication = indication,
	contentDescription = contentDescription,
	checkIcon = checkIcon,
)

@Composable
internal fun AnimatedCheckBox(
	value: ToggleableState,
	modifier: Modifier = Modifier,
	size: Dp = 26.dp,
	backgroundColor: Color,
	contentColor: Color,
	uncheckedBackgroundColor: Color,
	disabledBackgroundColor: Color,
	disabledContentColor: Color,
	enabled: Boolean,
	onClick: (() -> Unit)?,
	shape: Shape,
	borderColor: Color,
	borderWidth: Dp,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	contentDescription: String? = null,
	checkIcon: (@Composable (ToggleableState) -> Unit)? = null,
) {
	val currentOnClick = rememberUpdatedState(onClick)
	val hapticFeedback = LocalHapticFeedback.current
	val source = interactionSource ?: remember { MutableInteractionSource() }
	val pressed by source.collectIsPressedAsState()
	val scale by animateFloatAsState(
		targetValue = if (pressed && enabled && onClick != null) 0.86f else 1f,
		animationSpec = spring(dampingRatio = 0.65f, stiffness = 700f),
		label = "CheckBoxPressScale",
	)
	val transition = updateTransition(value, label = "CheckBoxTransition")
	val background by transition.animateColor(
		transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) },
		label = "CheckBoxBackground",
	) {
		when {
			!enabled                  -> disabledBackgroundColor
			it == ToggleableState.Off -> uncheckedBackgroundColor
			else                      -> backgroundColor
		}
	}
	val foreground by transition.animateColor(
		transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) },
		label = "CheckBoxForeground",
	) {
		when {
			!enabled                  -> disabledContentColor
			it == ToggleableState.Off -> contentColor
			else                      -> contentColor
		}
	}
	val checkAlpha by transition.animateFloat(
		transitionSpec = {
			if (targetState != ToggleableState.Off) tween(durationMillis = 10, easing = FastOutSlowInEasing)
			else tween(durationMillis = 150, easing = FastOutSlowInEasing)
		},
		label = "CheckBoxAlpha",
	) { if (it != ToggleableState.Off) 1f else 0f }
	val checkStartTrim by transition.animateFloat(
		transitionSpec = {
			if (targetState != ToggleableState.Off) tween(durationMillis = 200, easing = FastOutSlowInEasing)
			else keyframes {
				durationMillis = 300
				0.1f at 300
			}
		},
		label = "CheckBoxStartTrim",
	) { if (it != ToggleableState.Off) 0.186f else 0.1f }
	val checkEndTrim by transition.animateFloat(
		transitionSpec = {
			if (targetState != ToggleableState.Off) keyframes {
				durationMillis = 300
				0.85f at 200 using FastOutSlowInEasing
				0.803f at 300 using FastOutSlowInEasing
			} else keyframes {
				durationMillis = 300
				0.1f at 300
			}
		},
		label = "CheckBoxEndTrim",
	) { if (it != ToggleableState.Off) 0.803f else 0.1f }
	val centerGravitation by transition.animateFloat(
		transitionSpec = {
			if (targetState == ToggleableState.Indeterminate) tween(durationMillis = 200, easing = FastOutSlowInEasing)
			else tween(durationMillis = 150, easing = FastOutSlowInEasing)
		},
		label = "CheckBoxCenterGravitation",
	) { if (it == ToggleableState.Indeterminate) 1f else 0f }
	val path = remember { Path() }
	val semanticsModifier = contentDescription?.let { description ->
		Modifier.semantics { this.contentDescription = description }
	} ?: Modifier
	val toggleModifier = if (onClick != null) Modifier.triStateToggleable(
		state = value,
		enabled = enabled,
		role = Role.Checkbox,
		interactionSource = source,
		indication = indication,
		onClick = {
			currentOnClick.value?.invoke()
			hapticFeedback.performHapticFeedback(
				when (value) {
					ToggleableState.Off           -> HapticFeedbackType.ToggleOn
					ToggleableState.On            -> HapticFeedbackType.ToggleOff
					ToggleableState.Indeterminate -> HapticFeedbackType.SegmentTick
				},
			)
		},
	)
	else Modifier

	Box(
		modifier.wrapContentSize(Alignment.Center).size(size).graphicsLayer {
			scaleX = scale
			scaleY = scale
		}.clip(shape).drawWithCache {
			val viewportSize = 23f
			val strokeWidth = this.size.width * 0.09f
			val centerX = this.size.width / 2f
			val centerY = this.size.height / 2f
			val viewportCenter = viewportSize / 2f
			val cache = CheckmarkCache(
				startPoint = Offset(
					centerX + ((5f - viewportCenter) / viewportSize * this.size.width),
					centerY + ((9.4f - viewportCenter) / viewportSize * this.size.height),
				),
				middlePoint = Offset(
					centerX + ((10.3f - viewportCenter) / viewportSize * this.size.width),
					centerY + ((14.9f - viewportCenter) / viewportSize * this.size.height),
				),
				endPoint = Offset(
					centerX + ((17.9f - viewportCenter) / viewportSize * this.size.width),
					centerY + ((5.1f - viewportCenter) / viewportSize * this.size.height),
				),
				centerX = centerX,
				centerY = centerY,
				strokeWidth = strokeWidth,
			)
			val stroke = Stroke(
				width = strokeWidth,
				cap = StrokeCap.Round,
				join = StrokeJoin.Round,
				miter = 10f,
			)

			onDrawBehind {
				drawCircle(background)
				if (borderColor.isSpecified && borderWidth > 0.dp) drawCircle(
					color = borderColor,
					style = Stroke(width = borderWidth.toPx()),
				)
				if (checkIcon == null) drawTrimmedCheckmark(
					color = foreground,
					alpha = checkAlpha,
					trimStart = checkStartTrim,
					trimEnd = checkEndTrim,
					centerGravitation = centerGravitation,
					path = path,
					cache = cache,
					stroke = stroke,
				)
			}
		}.then(semanticsModifier).then(toggleModifier),
		Alignment.Center,
	) {
		if (checkIcon != null && value != ToggleableState.Off) ProvideContentColor(foreground) { checkIcon(value) }
	}
}

private data class CheckmarkCache(
	val startPoint: Offset,
	val middlePoint: Offset,
	val endPoint: Offset,
	val centerX: Float,
	val centerY: Float,
	val strokeWidth: Float,
)

private fun DrawScope.drawTrimmedCheckmark(
	color: Color,
	alpha: Float,
	trimStart: Float,
	trimEnd: Float,
	centerGravitation: Float,
	path: Path,
	cache: CheckmarkCache,
	stroke: Stroke,
) {
	path.rewind()

	val gravitatedStart = Offset(
		cache.startPoint.x,
		lerp(cache.startPoint.y, cache.centerY, centerGravitation),
	)
	val gravitatedMiddle = Offset(
		lerp(cache.middlePoint.x, cache.centerX, centerGravitation),
		lerp(cache.middlePoint.y, cache.centerY, centerGravitation),
	)
	val gravitatedEnd = Offset(
		cache.endPoint.x,
		lerp(cache.endPoint.y, cache.centerY, centerGravitation),
	)
	val firstSegmentLength = (gravitatedMiddle - gravitatedStart).getDistance()
	val secondSegmentLength = (gravitatedEnd - gravitatedMiddle).getDistance()
	val totalLength = firstSegmentLength + secondSegmentLength
	val startDistance = totalLength * trimStart
	val endDistance = totalLength * trimEnd

	if (startDistance < firstSegmentLength && endDistance > 0f) {
		val startRatio = (startDistance / firstSegmentLength).coerceIn(0f, 1f)
		val endRatio = (endDistance / firstSegmentLength).coerceIn(0f, 1f)
		path.moveTo(
			gravitatedStart.x + (gravitatedMiddle.x - gravitatedStart.x) * startRatio,
			gravitatedStart.y + (gravitatedMiddle.y - gravitatedStart.y) * startRatio,
		)
		path.lineTo(
			gravitatedStart.x + (gravitatedMiddle.x - gravitatedStart.x) * endRatio,
			gravitatedStart.y + (gravitatedMiddle.y - gravitatedStart.y) * endRatio,
		)
	}

	if (endDistance > firstSegmentLength) {
		val startRatio = ((startDistance - firstSegmentLength) / secondSegmentLength).coerceIn(0f, 1f)
		val endRatio = ((endDistance - firstSegmentLength) / secondSegmentLength).coerceIn(0f, 1f)
		val startX = gravitatedMiddle.x + (gravitatedEnd.x - gravitatedMiddle.x) * startRatio
		val startY = gravitatedMiddle.y + (gravitatedEnd.y - gravitatedMiddle.y) * startRatio
		val endX = gravitatedMiddle.x + (gravitatedEnd.x - gravitatedMiddle.x) * endRatio
		val endY = gravitatedMiddle.y + (gravitatedEnd.y - gravitatedMiddle.y) * endRatio

		if (startDistance < firstSegmentLength) path.lineTo(endX, endY)
		else {
			path.moveTo(startX, startY)
			path.lineTo(endX, endY)
		}
	}

	drawPath(
		path = path,
		color = color,
		alpha = alpha,
		style = stroke,
	)
}