@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.*
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun Button(
	onClick: () -> Unit,
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
	content: @Composable RowScope.() -> Unit,
) {
	val colors = YesTheme.colors
	val typography = YesTheme.typography
	val bgColor = when {
		backgroundColor.isSpecified -> backgroundColor
		enabled                     -> colors.backgroundVariant
		else                        -> colors.disabledBackground
	}
	val contentColor = when {
		contentColor.isSpecified -> contentColor
		enabled                  -> colors.textPrimary
		else                     -> colors.textTertiary
	}
	val indication = indication ?: LocalIndication.current
	val modifier =
		modifier.defaultMinSize(minWidth = 58.dp, minHeight = YesTheme.sizes.controlHeight).clip(shape).background(bgColor)
			.then(
				if (borderWidth > 0.dp && borderColor.isSpecified) Modifier.border(
					borderWidth, borderColor, shape
				) else Modifier
			)

	UnstyledButton(
		onClick = onClick,
		modifier = modifier,
		enabled = enabled,
		interactionSource = interactionSource,
		indication = indication,
		contentPadding = contentPadding,
	) {
		ProvideTextStyle(typography.button) {
			ProvideContentColor(contentColor) {
				Row(
					horizontalArrangement = horizontalArrangement,
					verticalAlignment = verticalAlignment,
					content = content,
				)
			}
		}
	}
}