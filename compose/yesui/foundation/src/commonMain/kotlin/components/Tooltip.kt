@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.*
import com.composeunstyled.*
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.TooltipPanel as UnstyledTooltipPanel
import com.composeunstyled.TooltipPlacement as UnstyledTooltipPlacement
import com.composeunstyled.TooltipScope as UnstyledTooltipScope

enum class FloatingSide { Top, Bottom, Start, End }

enum class FloatingAlignment { Start, Center, End }

@Immutable
data class TooltipPlacement(
	val side: FloatingSide = FloatingSide.Top,
	val alignment: FloatingAlignment = FloatingAlignment.Center,
	val positionAdjustment: IntOffset = IntOffset.Zero,
)

@Stable
class TooltipScope internal constructor(internal val delegate: UnstyledTooltipScope)

@Suppress("unused")
@Composable
fun Tooltip(
	enabled: Boolean = true,
	modifier: Modifier = Modifier,
	side: FloatingSide = FloatingSide.Top,
	alignment: FloatingAlignment = FloatingAlignment.Center,
	sideOffset: Dp = 6.dp,
	alignmentOffset: Dp = 0.dp,
	longPressShowDurationMillis: Long = 1500L,
	hoverDelayMillis: Long = 0L,
	panel: @Composable TooltipScope.() -> Unit,
	content: @Composable () -> Unit,
) = UnstyledTooltip(
	enabled = enabled,
	side = side.toUnstyled(),
	alignment = alignment.toUnstyled(),
	sideOffset = sideOffset,
	alignmentOffset = alignmentOffset,
	longPressShowDurationMillis = longPressShowDurationMillis,
	hoverDelayMillis = hoverDelayMillis,
	panel = { TooltipScope(this).panel() },
	anchor = { Box(modifier) { content() } },
)

@Suppress("unused")
@Composable
fun TooltipScope.TooltipPanel(
	modifier: Modifier = Modifier,
	enter: EnterTransition = defaultTooltipEnterTransition(),
	exit: ExitTransition = defaultTooltipExitTransition(),
	shape: Shape = YesTheme.shapes.medium,
	backgroundColor: Color = YesTheme.colors.background,
	contentColor: Color = YesTheme.colors.textPrimary,
	contentPadding: PaddingValues = PaddingValues(
		horizontal = YesTheme.sizes.spacingLarge,
		vertical = YesTheme.sizes.spacingMedium,
	),
	content: @Composable (TooltipPlacement) -> Unit,
) = with(delegate) {
	UnstyledTooltipPanel(
		modifier = modifier,
		enter = enter,
		exit = exit,
	) { placement ->
		Surface(
			shape = shape,
			backgroundColor = backgroundColor,
			contentColor = contentColor,
			contentPadding = contentPadding,
		) { content(placement.toYes()) }
	}
}

private fun FloatingSide.toUnstyled() = when (this) {
	FloatingSide.Top    -> AnchorSide.Top
	FloatingSide.Bottom -> AnchorSide.Bottom
	FloatingSide.Start  -> AnchorSide.Start
	FloatingSide.End    -> AnchorSide.End
}

private fun FloatingAlignment.toUnstyled() = when (this) {
	FloatingAlignment.Start  -> AnchorAlignment.Start
	FloatingAlignment.Center -> AnchorAlignment.Center
	FloatingAlignment.End    -> AnchorAlignment.End
}

private fun AnchorSide.toYes() = when (this) {
	AnchorSide.Top    -> FloatingSide.Top
	AnchorSide.Bottom -> FloatingSide.Bottom
	AnchorSide.Start  -> FloatingSide.Start
	AnchorSide.End    -> FloatingSide.End
}

private fun AnchorAlignment.toYes() = when (this) {
	AnchorAlignment.Start  -> FloatingAlignment.Start
	AnchorAlignment.Center -> FloatingAlignment.Center
	AnchorAlignment.End    -> FloatingAlignment.End
}

private fun UnstyledTooltipPlacement.toYes() = TooltipPlacement(
	side = side.toYes(),
	alignment = alignment.toYes(),
	positionAdjustment = positionAdjustment,
)

private fun defaultTooltipEnterTransition() = fadeIn(
	animationSpec = tween(durationMillis = 90),
) + scaleIn(
	animationSpec = tween(durationMillis = 120),
	initialScale = 0.96f,
)

private fun defaultTooltipExitTransition() = fadeOut(
	animationSpec = tween(durationMillis = 75),
) + scaleOut(
	animationSpec = tween(durationMillis = 90),
	targetScale = 0.98f,
)