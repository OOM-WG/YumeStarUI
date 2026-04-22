@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.buildModifier
import work.niggergo.yesui.core.theme.YesTheme
import work.niggergo.yesui.core.utils.YesShapeSize
import work.niggergo.yesui.core.utils.smoothShape

@Suppress("unused")
@Composable
fun Card(
	modifier: Modifier = Modifier,
	shape: Shape = smoothShape(YesShapeSize.Medium),
	backgroundColor: Color = Color.Unspecified,
	contentColor: Color = Color.Unspecified,
	borderColor: Color = Color.Unspecified,
	borderWidth: Dp = 0.dp,
	contentPadding: PaddingValues = PaddingValues(YesTheme.sizes.spacingPrimary),
	contentAlignment: Alignment = Alignment.TopStart,
	content: @Composable BoxScope.() -> Unit,
) {
	val colors = YesTheme.colors

	Surface(
		modifier = modifier.then(buildModifier {
			add(Modifier.clip(shape))
			if (borderWidth > 0.dp && borderColor.isSpecified) add(Modifier.border(borderWidth, borderColor, shape))
		}),
		backgroundColor = backgroundColor.takeIf { it.isSpecified } ?: colors.container,
		contentColor = contentColor.takeIf { it.isSpecified } ?: colors.content,
		contentPadding = contentPadding,
		contentAlignment = contentAlignment,
		content = content,
	)
}