@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.ProvideTextStyle
import com.composeunstyled.UnstyledButton
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
inline fun Button(
	noinline onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	shape: Shape = YesTheme.shapes.button,
	backgroundColor: Color = Color.Unspecified,
	contentColor: Color = Color.Unspecified,
	borderColor: Color = Color.Unspecified,
	borderWidth: Dp = 0.dp,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = null,
	contentPadding: PaddingValues = PaddingValues(
		horizontal = YesTheme.sizes.spacingExtraLarge,
		vertical = YesTheme.sizes.spacingLarge,
	),
	horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
	verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
	crossinline content: @Composable RowScope.() -> Unit,
) {
	val colors = YesTheme.colors
	val typography = YesTheme.typography
	val bgColor = when {
		backgroundColor.isSpecified -> backgroundColor
		enabled                     -> colors.backgroundVariant
		else                        -> colors.disabledBackground
	}
	val cntColor = when {
		contentColor.isSpecified -> contentColor
		enabled                  -> colors.textPrimary
		else                     -> colors.textTertiary
	}
	val indication = indication ?: LocalIndication.current

	UnstyledButton(
		onClick = onClick,
		modifier = modifier,
		enabled = enabled,
		shape = shape,
		backgroundColor = bgColor,
		contentColor = cntColor,
		borderColor = borderColor,
		borderWidth = borderWidth,
		interactionSource = interactionSource,
		indication = indication,
		contentPadding = contentPadding,
		horizontalArrangement = horizontalArrangement,
		verticalAlignment = verticalAlignment,
	) {
		ProvideTextStyle(typography.button) { content() }
	}
}