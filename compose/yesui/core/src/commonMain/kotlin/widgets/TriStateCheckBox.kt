@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

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
import work.niggergo.yesui.core.theme.YesTheme
import work.niggergo.yesui.core.utils.YesShapeSize
import work.niggergo.yesui.core.utils.smoothShape
import com.composeunstyled.TriStateCheckbox as UnstyledTriStateCheckbox

@Suppress("unused")
@Composable
fun TriStateCheckBox(
	value: ToggleableState,
	modifier: Modifier = Modifier,
	backgroundColor: Color = when (value) {
		ToggleableState.On            -> YesTheme.colors.tint
		ToggleableState.Indeterminate -> YesTheme.colors.surface
		ToggleableState.Off           -> YesTheme.colors.container
	},
	contentColor: Color = if (value == ToggleableState.On) YesTheme.colors.contentOnTint else YesTheme.colors.content,
	enabled: Boolean = true,
	onClick: () -> Unit,
	shape: Shape = smoothShape(YesShapeSize.Small),
	borderColor: Color = if (value == ToggleableState.On) YesTheme.colors.tint else YesTheme.colors.outline,
	borderWidth: Dp = 1.dp,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	contentDescription: String? = null,
	checkIcon: @Composable (ToggleableState) -> Unit,
) = UnstyledTriStateCheckbox(
	value = value,
	modifier = modifier,
	backgroundColor = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.56f),
	contentColor = if (enabled) contentColor else contentColor.copy(alpha = 0.56f),
	enabled = enabled,
	onClick = onClick,
	shape = shape,
	borderColor = if (enabled) borderColor else borderColor.copy(alpha = 0.56f),
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
		ToggleableState.On            -> YesTheme.colors.tint
		ToggleableState.Indeterminate -> YesTheme.colors.surface
		ToggleableState.Off           -> YesTheme.colors.container
	},
	contentColor: Color = if (value == ToggleableState.On) YesTheme.colors.contentOnTint else YesTheme.colors.content,
	enabled: Boolean = true,
	onClick: () -> Unit,
	shape: Shape = smoothShape(YesShapeSize.Small),
	borderColor: Color = if (value == ToggleableState.On) YesTheme.colors.tint else YesTheme.colors.outline,
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