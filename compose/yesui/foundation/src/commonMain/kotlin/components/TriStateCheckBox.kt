@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledTriStateCheckbox
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun TriStateCheckBox(
	value: ToggleableState,
	modifier: Modifier = Modifier,
	backgroundColor: Color = when (value) {
		ToggleableState.On            -> YesTheme.colors.brand
		ToggleableState.Indeterminate -> YesTheme.colors.brandVariant
		ToggleableState.Off           -> YesTheme.colors.divider
	},
	contentColor: Color = YesTheme.colors.onBrand,
	enabled: Boolean = true,
	onClick: () -> Unit,
	shape: Shape = YesTheme.shapes.pill,
	borderColor: Color = if (value == ToggleableState.Off) YesTheme.colors.divider else YesTheme.colors.brand,
	borderWidth: Dp = 1.dp,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	contentDescription: String? = null,
	checkIcon: @Composable (ToggleableState) -> Unit,
) = UnstyledTriStateCheckbox(
	value = value,
	modifier = modifier,
	backgroundColor = if (enabled) backgroundColor else if (value == ToggleableState.Off) YesTheme.colors.disabledBackgroundVariant else YesTheme.colors.disabledBrand,
	contentColor = if (enabled) contentColor else YesTheme.colors.disabledBackground,
	enabled = enabled,
	onClick = onClick,
	shape = shape,
	borderColor = if (enabled) borderColor else if (value == ToggleableState.Off) YesTheme.colors.disabledBackgroundVariant else YesTheme.colors.disabledBrand,
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
	backgroundColor: Color = when (value) {
		ToggleableState.On            -> YesTheme.colors.brand
		ToggleableState.Indeterminate -> YesTheme.colors.brandVariant
		ToggleableState.Off           -> YesTheme.colors.divider
	},
	contentColor: Color = YesTheme.colors.onBrand,
	enabled: Boolean = true,
	onClick: () -> Unit,
	shape: Shape = YesTheme.shapes.pill,
	borderColor: Color = if (value == ToggleableState.Off) YesTheme.colors.divider else YesTheme.colors.brand,
	borderWidth: Dp = 1.dp,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	contentDescription: String? = null,
	checkIcon: @Composable (ToggleableState) -> Unit,
) = TriStateCheckBox(
	value = value,
	modifier = modifier,
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
