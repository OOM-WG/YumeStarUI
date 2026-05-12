@file:Suppress("PackageDirectoryMismatch", "unused")

package work.niggergo.yesui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.foundation.components.Background
import work.niggergo.yesui.foundation.components.Card
import work.niggergo.yesui.foundation.theme.YesTheme

@Composable
fun BackgroundImageCard(
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.card,
	gradientColor: Color = YesTheme.colors.surface,
	contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 28.dp),
	backgroundContent: @Composable BoxScope.() -> Unit = {
		Background(modifier = Modifier.fillMaxSize())
	},
	content: @Composable BoxScope.() -> Unit,
) = Card(
	modifier = modifier,
	shape = shape,
	backgroundColor = Color.Transparent,
	contentPadding = PaddingValues(0.dp),
) {
	Box(Modifier.fillMaxSize()) {
		backgroundContent()

		Box(
			Modifier.fillMaxSize().background(
				Brush.verticalGradient(
					colorStops = arrayOf(
						0f to Color.Transparent,
						0.58f to Color.Transparent,
						0.82f to gradientColor.copy(alpha = 0.84f),
						1f to gradientColor,
					),
				),
			)
		)

		Box(
			Modifier.fillMaxSize().padding(contentPadding),
			content = content,
		)
	}
}