@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import com.composeunstyled.ProvideContentColor
import com.composeunstyled.buildModifier

@Suppress("unused")
@Composable
fun Surface(
	modifier: Modifier = Modifier,
	backgroundColor: Color = Color.Unspecified,
	contentColor: Color = Color.Unspecified,
	contentPadding: PaddingValues = PaddingValues(),
	contentAlignment: Alignment = Alignment.TopStart,
	propagateMinConstraints: Boolean = false,
	content: @Composable BoxScope.() -> Unit,
) = Box(
	modifier = modifier.then(buildModifier {
		if (backgroundColor.isSpecified) add(Modifier.background(backgroundColor))
	}),
	contentAlignment = contentAlignment,
) {
	ProvideContentColor(contentColor) {
		Box(
			modifier = Modifier.padding(contentPadding),
			contentAlignment = contentAlignment,
			propagateMinConstraints = propagateMinConstraints,
			content = content,
		)
	}
}