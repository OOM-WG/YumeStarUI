@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.composeunstyled.UnstyledProgress
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.ProgressScope as UnstyledProgressScope

@Suppress("unused")
@Stable
class ProgressIndicatorScope internal constructor(internal val delegate: UnstyledProgressScope) {
	val progress get() = delegate.progress
}

@Suppress("unused")
@Composable
fun ProgressIndicator(
	progress: Float,
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.pill,
	backgroundColor: Color = YesTheme.colors.backgroundVariant,
	contentColor: Color = YesTheme.colors.tint,
	content: @Composable ProgressIndicatorScope.() -> Unit = { ProgressBar(color = contentColor) },
) = UnstyledProgress(
	progress = progress,
	modifier = modifier.clip(shape).background(backgroundColor),
	content = { ProgressIndicatorScope(this).content() },
)

@Suppress("unused")
@Composable
fun ProgressIndicator(
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.pill,
	backgroundColor: Color = YesTheme.colors.backgroundVariant,
	contentColor: Color = YesTheme.colors.tint,
	content: @Composable () -> Unit = { IndeterminateProgressBar(color = contentColor) },
) = UnstyledProgress(
	modifier = modifier.clip(shape).background(backgroundColor),
	content = content,
)

@Suppress("unused")
@Composable
fun ProgressIndicatorScope.ProgressBar(
	shape: Shape = YesTheme.shapes.pill,
	color: Color = YesTheme.colors.tint,
) = with(delegate) {
	Box(Modifier.fillMaxWidth(progress.coerceIn(0f, 1f)).fillMaxHeight().background(color, shape))
}

@Suppress("unused")
@Composable
fun IndeterminateProgressBar(color: Color = YesTheme.colors.tint) {
	val transition = rememberInfiniteTransition(label = "IndeterminateProgress")
	val position by transition.animateFloat(
		initialValue = 0f,
		targetValue = 1f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 920, easing = LinearEasing),
			repeatMode = RepeatMode.Restart,
		),
		label = "IndeterminateProgressPosition",
	)

	Canvas(Modifier.fillMaxSize()) {
		val width = size.width * 0.42f
		val x = (size.width + width) * position - width
		drawRoundRect(
			color = color,
			topLeft = Offset(x, 0f),
			size = Size(width, size.height),
			cornerRadius = CornerRadius(size.height / 2f, size.height / 2f),
		)
	}
}