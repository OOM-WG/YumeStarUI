@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.composeunstyled.UnstyledProgressBar
import com.composeunstyled.UnstyledProgressIndicator
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.ProgressIndicatorScope as UnstyledProgressIndicatorScope

typealias ProgressIndicatorScope = UnstyledProgressIndicatorScope

@Suppress("unused")
@Composable
fun ProgressIndicator(
	progress: Float,
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.pill,
	backgroundColor: Color = YesTheme.colors.backgroundVariant,
	contentColor: Color = YesTheme.colors.brand,
	content: @Composable ProgressIndicatorScope.() -> Unit = { ProgressBar() },
) = UnstyledProgressIndicator(
	progress = progress,
	modifier = modifier,
	shape = shape,
	backgroundColor = backgroundColor,
	contentColor = contentColor,
	content = content,
)

@Suppress("unused")
@Composable
fun ProgressIndicator(
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.pill,
	backgroundColor: Color = YesTheme.colors.backgroundVariant,
	contentColor: Color = YesTheme.colors.brand,
	content: @Composable () -> Unit,
) = UnstyledProgressIndicator(
	modifier = modifier,
	shape = shape,
	backgroundColor = backgroundColor,
	contentColor = contentColor,
	content = content,
)

@Suppress("unused")
@Composable
fun ProgressIndicatorScope.ProgressBar(
	shape: Shape = YesTheme.shapes.pill,
	color: Color = YesTheme.colors.brand,
) = UnstyledProgressBar(
	shape = shape,
	color = color,
)