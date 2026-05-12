@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.core.*
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledBottomSheet
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.BottomSheetScope as UnstyledBottomSheetScope
import com.composeunstyled.BottomSheetState as UnstyledBottomSheetState
import com.composeunstyled.DragIndication as UnstyledBottomSheetDragIndication
import com.composeunstyled.Sheet as UnstyledBottomSheetPanel
import com.composeunstyled.SheetDetent as UnstyledSheetDetent
import com.composeunstyled.rememberBottomSheetState as rememberUnstyledBottomSheetState

@Suppress("unused")
@Stable
class BottomSheetState internal constructor(internal val delegate: UnstyledBottomSheetState) {
	val currentDetent get() = delegate.currentDetent.toYes()

	var targetDetent
		get() = delegate.targetDetent.toYes()
		set(value) {
			delegate.targetDetent = value.delegate
		}

	var detents
		get() = delegate.detents.map { it.toYes() }
		set(value) {
			delegate.detents = value.map { it.delegate }
		}

	val isIdle get() = delegate.isIdle

	val offset get() = delegate.offset

	fun progress(from: SheetDetent, to: SheetDetent) = delegate.progress(from.delegate, to.delegate)

	suspend fun animateTo(
		detent: SheetDetent,
		animationSpec: AnimationSpec<Float>? = null,
	) = delegate.animateTo(detent.delegate, animationSpec)

	fun jumpTo(detent: SheetDetent) = delegate.jumpTo(detent.delegate)

	fun invalidateDetents() = delegate.invalidateDetents()
}

@Stable
class BottomSheetScope internal constructor(internal val delegate: UnstyledBottomSheetScope)

@Stable
class BottomSheetPanelScope internal constructor(internal val delegate: UnstyledBottomSheetScope)

@Suppress("unused")
@Composable
fun rememberBottomSheetState(
	initialDetent: SheetDetent,
	detents: List<SheetDetent> = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
	animationSpec: AnimationSpec<Float> = tween(),
	confirmDetentChange: (SheetDetent) -> Boolean = { true },
	decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
	velocityThreshold: () -> Dp = { 125.dp },
	positionalThreshold: (totalDistance: Dp) -> Dp = { 56.dp },
): BottomSheetState {
	val delegate = rememberUnstyledBottomSheetState(
		initialDetent = initialDetent.delegate,
		detents = detents.map { it.delegate },
		animationSpec = animationSpec,
		confirmDetentChange = { confirmDetentChange(it.toYes()) },
		decayAnimationSpec = decayAnimationSpec,
		velocityThreshold = velocityThreshold,
		positionalThreshold = positionalThreshold,
	)
	return remember(delegate) { BottomSheetState(delegate) }
}

@Suppress("unused")
@Composable
fun BottomSheet(
	state: BottomSheetState,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	offsetForIme: Boolean = false,
	content: @Composable BottomSheetScope.() -> Unit,
) = UnstyledBottomSheet(
	state = state.delegate,
	modifier = modifier,
	enabled = enabled,
	offsetForIme = offsetForIme,
	content = { BottomSheetScope(this).content() },
)

@Suppress("unused")
@Composable
fun BottomSheetScope.BottomSheetPanel(
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.sheet,
	backgroundColor: Color = YesTheme.colors.background,
	contentColor: Color = YesTheme.colors.textPrimary,
	contentPadding: PaddingValues = PaddingValues(YesTheme.sizes.spacingExtraLarge),
	content: @Composable BottomSheetPanelScope.() -> Unit,
) = with(delegate) {
	UnstyledBottomSheetPanel(modifier = modifier) {
		Surface(
			shape = shape,
			backgroundColor = backgroundColor,
			contentColor = contentColor,
			contentPadding = contentPadding,
			content = { BottomSheetPanelScope(delegate).content() },
		)
	}
}

@Suppress("unused")
@Composable
fun BottomSheetScope.BottomSheetDragHandle(
	modifier: Modifier = Modifier,
	width: Dp = 44.dp,
	height: Dp = 4.dp,
	color: Color = YesTheme.colors.divider,
	shape: Shape = YesTheme.shapes.pill,
	indication: Indication? = LocalIndication.current,
	interactionSource: MutableInteractionSource? = null,
) = with(delegate) {
	UnstyledBottomSheetDragIndication(
		modifier = modifier.width(width).height(height).background(color, shape),
		indication = indication,
		interactionSource = interactionSource,
	)
}

@Suppress("unused")
@Composable
fun BottomSheetPanelScope.BottomSheetDragHandle(
	modifier: Modifier = Modifier,
	width: Dp = 44.dp,
	height: Dp = 4.dp,
	color: Color = YesTheme.colors.divider,
	shape: Shape = YesTheme.shapes.pill,
	indication: Indication? = LocalIndication.current,
	interactionSource: MutableInteractionSource? = null,
) = with(delegate) {
	UnstyledBottomSheetDragIndication(
		modifier = modifier.width(width).height(height).background(color, shape),
		indication = indication,
		interactionSource = interactionSource,
	)
}

private fun UnstyledSheetDetent.toYes() = when (this) {
	UnstyledSheetDetent.Hidden        -> SheetDetent.Hidden
	UnstyledSheetDetent.FullyExpanded -> SheetDetent.FullyExpanded
	else                              -> SheetDetent(this)
}