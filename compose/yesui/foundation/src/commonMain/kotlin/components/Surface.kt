@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.ProvideContentColor

@Suppress("unused")
@Composable
fun Surface(
	modifier: Modifier = Modifier,
	shape: Shape = RectangleShape,
	backgroundColor: Color = Color.Unspecified,
	contentColor: Color = Color.Unspecified,
	borderColor: Color = Color.Unspecified,
	borderWidth: Dp = 0.dp,
	contentPadding: PaddingValues = PaddingValues(),
	contentAlignment: Alignment = Alignment.TopStart,
	propagateMinConstraints: Boolean = false,
	content: @Composable BoxScope.() -> Unit,
) {
	var modifier = modifier.clip(shape)
	if (backgroundColor.isSpecified) modifier = modifier.background(backgroundColor)
	if (borderWidth > 0.dp && borderColor.isSpecified) modifier = modifier.border(borderWidth, borderColor, shape)

	Box(modifier, contentAlignment) {
		ProvideContentColor(contentColor) {
			Box(
				Modifier.padding(contentPadding),
				contentAlignment,
				propagateMinConstraints,
				content,
			)
		}
	}
}