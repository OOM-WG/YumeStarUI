@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun Card(
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.card,
	backgroundColor: Color = Color.Unspecified,
	contentColor: Color = Color.Unspecified,
	borderColor: Color = Color.Unspecified,
	borderWidth: Dp = 0.dp,
	contentPadding: PaddingValues = PaddingValues(YesTheme.sizes.spacingExtraLarge),
	contentAlignment: Alignment = Alignment.TopStart,
	content: @Composable BoxScope.() -> Unit,
) {
	val colors = YesTheme.colors

	Surface(
		modifier = modifier,
		shape = shape,
		backgroundColor = backgroundColor.takeIf { it.isSpecified } ?: colors.background,
		contentColor = contentColor.takeIf { it.isSpecified } ?: colors.textPrimary,
		borderColor = borderColor,
		borderWidth = borderWidth,
		contentPadding = contentPadding,
		contentAlignment = contentAlignment,
		content = content,
	)
}