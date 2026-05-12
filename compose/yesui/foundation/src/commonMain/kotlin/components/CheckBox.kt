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
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun CheckBox(
	checked: Boolean,
	modifier: Modifier = Modifier,
	size: Dp = 26.dp,
	backgroundColor: Color = if (checked) YesTheme.colors.tint else YesTheme.colors.divider,
	contentColor: Color = YesTheme.colors.onTint,
	enabled: Boolean = true,
	onCheckedChange: ((Boolean) -> Unit)? = null,
	shape: Shape = YesTheme.shapes.pill,
	borderColor: Color = Color.Unspecified,
	borderWidth: Dp = 0.dp,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	contentDescription: String? = null,
	checkIcon: (@Composable () -> Unit)? = null,
) = AnimatedCheckBox(
	value = if (checked) ToggleableState.On else ToggleableState.Off,
	modifier = modifier,
	size = size,
	backgroundColor = backgroundColor,
	contentColor = contentColor,
	uncheckedBackgroundColor = YesTheme.colors.divider,
	disabledBackgroundColor = YesTheme.colors.disabledBackgroundVariant,
	disabledContentColor = YesTheme.colors.disabledBackground,
	enabled = enabled,
	onClick = onCheckedChange?.let { { it(!checked) } },
	shape = shape,
	borderColor = borderColor,
	borderWidth = borderWidth,
	interactionSource = interactionSource,
	indication = indication,
	contentDescription = contentDescription,
	checkIcon = checkIcon?.let { icon -> { state -> if (state == ToggleableState.On) icon() } },
)