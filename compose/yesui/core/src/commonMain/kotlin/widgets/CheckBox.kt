@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledCheckbox
import work.niggergo.yesui.core.theme.YesTheme
import work.niggergo.yesui.core.utils.YesShapeSize
import work.niggergo.yesui.core.utils.smoothShape

@Suppress("unused")
@Composable
fun CheckBox(
	checked: Boolean,
	modifier: Modifier = Modifier,
	backgroundColor: Color = if (checked) YesTheme.colors.tint else YesTheme.colors.container,
	contentColor: Color = if (checked) YesTheme.colors.contentOnTint else YesTheme.colors.content,
	enabled: Boolean = true,
	onCheckedChange: ((Boolean) -> Unit)? = null,
	shape: Shape = smoothShape(YesShapeSize.Small),
	borderColor: Color = if (checked) YesTheme.colors.tint else YesTheme.colors.outline,
	borderWidth: Dp = 1.dp,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	contentDescription: String? = null,
	checkIcon: @Composable () -> Unit,
) = UnstyledCheckbox(
	checked = checked,
	modifier = modifier,
	backgroundColor = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.56f),
	contentColor = if (enabled) contentColor else contentColor.copy(alpha = 0.56f),
	enabled = enabled,
	onCheckedChange = onCheckedChange,
	shape = shape,
	borderColor = if (enabled) borderColor else borderColor.copy(alpha = 0.56f),
	borderWidth = borderWidth,
	interactionSource = interactionSource,
	indication = indication,
	contentDescription = contentDescription,
	checkIcon = checkIcon,
)