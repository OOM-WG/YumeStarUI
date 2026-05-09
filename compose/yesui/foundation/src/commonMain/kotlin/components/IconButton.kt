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
import androidx.compose.ui.unit.*
import com.composeunstyled.LocalContentColor
import com.composeunstyled.UnstyledButton
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
	val cntColor = when {
		contentColor.isSpecified -> contentColor
		enabled                  -> LocalContentColor.current.takeOrElse { colors.textPrimary }
		else                     -> colors.textTertiary
	}
	val indication = indication ?: LocalIndication.current

	UnstyledButton(
		onClick = onClick,
		modifier = Modifier.size(size).then(modifier),
		enabled = enabled,
		shape = shape,
		backgroundColor = bgColor,
		contentColor = cntColor,
		interactionSource = interactionSource,
		indication = indication,
		contentPadding = contentPadding,
		borderColor = borderColor,
		borderWidth = borderWidth,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center,
	) {
		Box(
			modifier = Modifier.fillMaxSize(),
			contentAlignment = Alignment.Center,
			content = content,
		)
	}
}