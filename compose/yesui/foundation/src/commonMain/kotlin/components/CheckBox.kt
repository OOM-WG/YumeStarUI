@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

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
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun CheckBox(
	checked: Boolean,
	modifier: Modifier = Modifier,
	backgroundColor: Color = if (checked) YesTheme.colors.brand else YesTheme.colors.divider,
	contentColor: Color = YesTheme.colors.onBrand,
	enabled: Boolean = true,
	onCheckedChange: ((Boolean) -> Unit)? = null,
	shape: Shape = YesTheme.shapes.pill,
	borderColor: Color = if (checked) YesTheme.colors.brand else YesTheme.colors.divider,
	borderWidth: Dp = 1.dp,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	contentDescription: String? = null,
	checkIcon: @Composable () -> Unit,
) = UnstyledCheckbox(
	checked = checked,
	modifier = modifier,
	backgroundColor = if (enabled) backgroundColor else if (checked) YesTheme.colors.disabledBrand else YesTheme.colors.disabledBackgroundVariant,
	contentColor = if (enabled) contentColor else YesTheme.colors.disabledBackground,
	enabled = enabled,
	onCheckedChange = onCheckedChange,
	shape = shape,
	borderColor = if (enabled) borderColor else if (checked) YesTheme.colors.disabledBrand else YesTheme.colors.disabledBackgroundVariant,
	borderWidth = borderWidth,
	interactionSource = interactionSource,
	indication = indication,
	contentDescription = contentDescription,
	checkIcon = checkIcon,
)