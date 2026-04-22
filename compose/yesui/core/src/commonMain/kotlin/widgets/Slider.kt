@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.core.theme.YesTheme
import work.niggergo.yesui.core.utils.smoothCapsuleShape
import com.composeunstyled.Slider as UnstyledSlider
import com.composeunstyled.SliderState as UnstyledSliderState
import com.composeunstyled.Thumb as UnstyledThumb
import com.composeunstyled.rememberSliderState as rememberUnstyledSliderState

typealias SliderState = UnstyledSliderState

@Suppress("unused")
@Composable
fun rememberSliderState(
	initialValue: Float = 0.0f,
	valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
	steps: Int = 0,
): SliderState = rememberUnstyledSliderState(
	initialValue = initialValue,
	valueRange = valueRange,
	steps = steps,
)

@Suppress("unused")
@Composable
fun Slider(
	state: SliderState,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	interactionSource: MutableInteractionSource? = null,
	valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
	orientation: Orientation = Orientation.Horizontal,
	track: @Composable () -> Unit = { DefaultSliderTrack(state = state, valueRange = valueRange, enabled = enabled) },
	thumb: @Composable () -> Unit = {
		Thumb(
			shape = smoothCapsuleShape(),
			color = if (enabled) YesTheme.colors.tint else YesTheme.colors.tint.copy(alpha = 0.56f),
		)
	},
) = UnstyledSlider(
	state = state,
	modifier = modifier,
	enabled = enabled,
	interactionSource = interactionSource,
	valueRange = valueRange,
	orientation = orientation,
	track = track,
	thumb = thumb,
)

@Suppress("unused")
@Composable
fun Thumb(
	modifier: Modifier = Modifier,
	shape: Shape = smoothCapsuleShape(),
	color: Color = YesTheme.colors.tint,
) = UnstyledThumb(
	modifier = modifier,
	shape = shape,
	color = color,
)

@Composable
private fun DefaultSliderTrack(
	state: SliderState,
	valueRange: ClosedFloatingPointRange<Float>,
	enabled: Boolean,
) {
	val baseColor = YesTheme.colors.container
	val progressColor = YesTheme.colors.tint
	val fraction = ((state.value - valueRange.start) / (valueRange.endInclusive - valueRange.start)).coerceIn(0f, 1f)
	val trackShape = smoothCapsuleShape()

	Box(
		Modifier.fillMaxWidth().height(4.dp).background(if (enabled) baseColor else baseColor.copy(alpha = 0.56f), trackShape)
	) {
		Box(
			Modifier.fillMaxWidth(fraction).height(4.dp)
				.background(if (enabled) progressColor else progressColor.copy(alpha = 0.56f), trackShape)
		)
	}
}