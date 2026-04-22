@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import work.niggergo.yesui.core.theme.YesTheme
import work.niggergo.yesui.core.utils.smoothCapsuleShape
import com.composeunstyled.ProgressBar as UnstyledProgressBar
import com.composeunstyled.ProgressIndicator as UnstyledProgressIndicator
import com.composeunstyled.ProgressIndicatorScope as UnstyledProgressIndicatorScope

typealias ProgressIndicatorScope = UnstyledProgressIndicatorScope

@Suppress("unused")
@Composable
fun ProgressIndicator(
	progress: Float,
	modifier: Modifier = Modifier,
	shape: Shape = smoothCapsuleShape(),
	backgroundColor: Color = YesTheme.colors.container,
	contentColor: Color = YesTheme.colors.tint,
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
	shape: Shape = smoothCapsuleShape(),
	backgroundColor: Color = YesTheme.colors.container,
	contentColor: Color = YesTheme.colors.tint,
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
	shape: Shape = smoothCapsuleShape(),
	color: Color = YesTheme.colors.tint,
) = UnstyledProgressBar(
	shape = shape,
	color = color,
)