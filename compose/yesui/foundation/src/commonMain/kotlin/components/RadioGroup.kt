@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.composeunstyled.UnstyledRadioButton
import com.composeunstyled.UnstyledRadioGroup
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun RadioGroup(
	value: String?,
	onValueChange: (String) -> Unit,
	contentDescription: String?,
	modifier: Modifier = Modifier,
	content: @Composable ColumnScope.() -> Unit,
) = UnstyledRadioGroup(
	value = value,
	onValueChange = onValueChange,
	contentDescription = contentDescription,
	modifier = modifier,
	content = content,
)

@Suppress("unused")
@Composable
fun RadioButton(
	value: String,
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.medium,
	backgroundColor: Color = Color.Transparent,
	contentColor: Color = YesTheme.colors.textPrimary,
	selectedColor: Color = YesTheme.colors.brand,
	enabled: Boolean = true,
	contentPadding: PaddingValues = PaddingValues(
		horizontal = YesTheme.sizes.spacingLarge,
		vertical = YesTheme.sizes.spacingMedium,
	),
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
	verticalAlignment: Alignment.Vertical = Alignment.Top,
	content: @Composable (RowScope.() -> Unit),
) = UnstyledRadioButton(
	value = value,
	modifier = modifier,
	shape = shape,
	backgroundColor = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.56f),
	contentColor = if (enabled) contentColor else contentColor.copy(alpha = 0.56f),
	selectedColor = if (enabled) selectedColor else selectedColor.copy(alpha = 0.56f),
	enabled = enabled,
	contentPadding = contentPadding,
	interactionSource = interactionSource,
	indication = indication,
	horizontalArrangement = horizontalArrangement,
	verticalAlignment = verticalAlignment,
	content = content,
)