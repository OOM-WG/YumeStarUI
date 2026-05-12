@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledRadioGroup
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.RadioButton as UnstyledRadioButton
import com.composeunstyled.RadioGroupScope as UnstyledRadioGroupScope

@Stable
class RadioGroupScope<T> internal constructor(
	internal val delegate: UnstyledRadioGroupScope,
	internal val value: T?,
	columnScope: ColumnScope,
) : ColumnScope by columnScope

@Suppress("unused")
@Composable
fun <T> RadioGroup(
	value: T?,
	onValueChange: (T) -> Unit,
	contentDescription: String? = null,
	modifier: Modifier = Modifier,
	verticalArrangement: Arrangement.Vertical = Arrangement.Top,
	horizontalAlignment: Alignment.Horizontal = Alignment.Start,
	content: @Composable RadioGroupScope<T>.() -> Unit,
) = UnstyledRadioGroup(
	value = value,
	onValueChange = onValueChange,
	accessibilityLabel = contentDescription,
	modifier = modifier,
) {
	val groupDelegate = this
	Column(
		verticalArrangement = verticalArrangement,
		horizontalAlignment = horizontalAlignment,
	) {
		RadioGroupScope(groupDelegate, value, this).content()
	}
}

@Suppress("unused")
@Composable
fun <T> RadioGroupScope<T>.RadioButton(
	value: T,
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.medium,
	backgroundColor: Color = Color.Transparent,
	contentColor: Color = YesTheme.colors.textPrimary,
	selectedColor: Color = YesTheme.colors.tint,
	enabled: Boolean = true,
	contentPadding: PaddingValues = PaddingValues(
		horizontal = YesTheme.sizes.spacingLarge,
		vertical = YesTheme.sizes.spacingMedium,
	),
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
	verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
	content: @Composable RowScope.() -> Unit,
) = with(delegate) {
	val selected = this@RadioButton.value == value
	UnstyledRadioButton(
		value = value,
		modifier = modifier.clip(shape),
		enabled = enabled,
		interactionSource = interactionSource,
		indication = indication,
	) {
		Surface(
			shape = shape,
			backgroundColor = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.56f),
			contentColor = if (enabled) contentColor else contentColor.copy(alpha = 0.56f),
			contentPadding = contentPadding,
		) {
			Row(
				Modifier.fillMaxWidth(),
				horizontalArrangement = horizontalArrangement,
				verticalAlignment = verticalAlignment,
			) {
				RadioIndicator(
					selected = selected,
					enabled = enabled,
					selectedColor = selectedColor,
				)
				Spacer(Modifier.width(YesTheme.sizes.spacingMedium))
				content()
			}
		}
	}
}

@Composable
private fun RadioIndicator(
	selected: Boolean,
	enabled: Boolean,
	selectedColor: Color,
) {
	val colors = YesTheme.colors
	val color = when {
		!enabled -> colors.disabledBackgroundVariant
		selected -> selectedColor
		else     -> colors.divider
	}

	Box(
		Modifier.size(18.dp).border(2.dp, color, YesTheme.shapes.pill),
		Alignment.Center,
	) {
		if (selected) Box(Modifier.size(10.dp).background(color, YesTheme.shapes.pill))
	}
}