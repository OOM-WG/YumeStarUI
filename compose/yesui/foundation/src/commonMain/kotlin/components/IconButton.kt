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
import androidx.compose.ui.unit.*
import com.composeunstyled.*
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun IconButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	shape: Shape = YesTheme.shapes.iconButton,
	backgroundColor: Color = Color.Unspecified,
	contentColor: Color = Color.Unspecified,
	borderColor: Color = Color.Unspecified,
	borderWidth: Dp = 0.dp,
	size: Dp = Dp.Unspecified,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = null,
	contentPadding: PaddingValues = PaddingValues(),
	content: @Composable BoxScope.() -> Unit,
) {
	val colors = YesTheme.colors
	val sizes = YesTheme.sizes
	val size = if (size.isSpecified) size else sizes.touchTarget
	val bgColor = when {
		backgroundColor.isSpecified -> backgroundColor
		else                        -> Color.Transparent
	}
	val contentColor = when {
		contentColor.isSpecified -> contentColor
		enabled                  -> LocalContentColor.current.takeOrElse { colors.textPrimary }
		else                     -> colors.textTertiary
	}
	val indication = indication ?: LocalIndication.current
	val modifier = Modifier.size(size).then(modifier).clip(shape).background(bgColor).then(
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
		contentAlignment = Alignment.Center,
	) {
		ProvideContentColor(contentColor) {
			Box(
				Modifier.fillMaxSize(),
				Alignment.Center,
				content = content,
			)
		}
	}
}