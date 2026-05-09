@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledSlider
import com.composeunstyled.UnstyledThumb
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.SliderState as UnstyledSliderState
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
			shape = YesTheme.shapes.pill,
			color = if (enabled) YesTheme.colors.brand else YesTheme.colors.disabledBrand,
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
	shape: Shape = YesTheme.shapes.pill,
	color: Color = YesTheme.colors.brand,
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
	val baseColor = YesTheme.colors.backgroundVariant
	val progressColor = YesTheme.colors.brand
	val fraction = ((state.value - valueRange.start) / (valueRange.endInclusive - valueRange.start)).coerceIn(0f, 1f)
	val trackShape = YesTheme.shapes.pill

	Box(
		Modifier.fillMaxWidth().height(4.dp).background(if (enabled) baseColor else baseColor.copy(alpha = 0.56f), trackShape)
	) {
		Box(
			Modifier.fillMaxWidth(fraction).height(4.dp)
				.background(if (enabled) progressColor else progressColor.copy(alpha = 0.56f), trackShape)
		)
	}
}