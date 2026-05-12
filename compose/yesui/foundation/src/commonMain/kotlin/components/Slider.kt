@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledSlider
import work.niggergo.yesui.foundation.theme.YesTheme

@Stable
class SliderState(
	initialValue: Float = 0.0f,
	val valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
	val steps: Int = 0,
) {
	var value by mutableFloatStateOf(initialValue.coerceIn(valueRange.start, valueRange.endInclusive))
}

@Suppress("unused")
@Composable
fun rememberSliderState(
	initialValue: Float = 0.0f,
	valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
	steps: Int = 0,
) = remember(valueRange, steps) { SliderState(initialValue, valueRange, steps) }

@Suppress("unused")
@Composable
fun Slider(
	state: SliderState,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	interactionSource: MutableInteractionSource? = null,
	valueRange: ClosedFloatingPointRange<Float> = state.valueRange,
	steps: Int = state.steps,
	orientation: Orientation = Orientation.Horizontal,
	reverseDirection: Boolean = false,
	onValueChangeFinished: (() -> Unit)? = null,
	track: @Composable () -> Unit = {
		DefaultSliderTrack(
			state = state,
			valueRange = valueRange,
			enabled = enabled,
			orientation = orientation,
			reverseDirection = reverseDirection,
		)
	},
	thumb: @Composable () -> Unit = {
		Thumb(
			shape = YesTheme.shapes.pill,
			color = if (enabled) YesTheme.colors.tint else YesTheme.colors.disabledTint,
		)
	},
) = UnstyledSlider(
	value = state.value,
	onValueChange = { state.value = it },
	modifier = modifier,
	enabled = enabled,
	interactionSource = interactionSource,
	valueRange = valueRange,
	steps = steps,
	onValueChangeFinished = onValueChangeFinished,
	orientation = orientation,
	reverseDirection = reverseDirection,
	track = { track() },
	thumb = { thumb() },
)

@Suppress("unused")
@Composable
fun Thumb(
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.pill,
	color: Color = YesTheme.colors.tint,
) = Box(modifier.size(20.dp).background(color, shape))

@Composable
private fun DefaultSliderTrack(
	state: SliderState,
	valueRange: ClosedFloatingPointRange<Float>,
	enabled: Boolean,
	orientation: Orientation,
	reverseDirection: Boolean,
) {
	val baseColor = YesTheme.colors.backgroundVariant
	val progressColor = YesTheme.colors.tint
	val denominator = valueRange.endInclusive - valueRange.start
	val fraction = if (denominator == 0f) 0f
	else ((state.value - valueRange.start) / denominator).coerceIn(0f, 1f)
	val trackShape = YesTheme.shapes.pill
	val baseModifier = if (orientation == Orientation.Horizontal) Modifier.fillMaxWidth().height(4.dp)
	else Modifier.fillMaxHeight().width(4.dp)
	val progressModifier = if (orientation == Orientation.Horizontal) Modifier.fillMaxWidth(fraction).fillMaxHeight()
	else Modifier.fillMaxWidth().fillMaxHeight(fraction)
	val progressAlignment = when (orientation) {
		Orientation.Horizontal -> if (reverseDirection) Alignment.CenterEnd else Alignment.CenterStart
		Orientation.Vertical   -> if (reverseDirection) Alignment.TopCenter else Alignment.BottomCenter
	}

	Box(
		baseModifier.background(if (enabled) baseColor else baseColor.copy(alpha = 0.56f), trackShape),
		progressAlignment,
	) {
		Box(progressModifier.background(if (enabled) progressColor else progressColor.copy(alpha = 0.56f), trackShape))
	}
}