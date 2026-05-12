@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledModalBottomSheet
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.ModalBottomSheetProperties as UnstyledModalSheetProperties
import com.composeunstyled.ModalBottomSheetScope as UnstyledModalBottomSheetScope
import com.composeunstyled.ModalBottomSheetState as UnstyledModalBottomSheetState
import com.composeunstyled.Scrim as UnstyledModalBottomSheetScrim
import com.composeunstyled.Sheet as UnstyledModalBottomSheetPanel
import com.composeunstyled.SheetDetent as UnstyledSheetDetent
import com.composeunstyled.rememberModalBottomSheetState as rememberUnstyledModalBottomSheetState

@Suppress("unused")
@Immutable
class SheetDetent internal constructor(internal val delegate: UnstyledSheetDetent) {
	constructor(
		identifier: String,
		calculateDetentHeight: (containerHeight: Dp, sheetHeight: Dp) -> Dp,
	) : this(UnstyledSheetDetent(identifier, calculateDetentHeight))

	val identifier get() = delegate.identifier

	val calculateDetentHeight get() = delegate.calculateDetentHeight

	override fun equals(other: Any?) = other is SheetDetent && delegate == other.delegate
	override fun hashCode() = delegate.hashCode()
	override fun toString() = delegate.toString()

	companion object {
		val Hidden = SheetDetent(UnstyledSheetDetent.Hidden)
		val FullyExpanded = SheetDetent(UnstyledSheetDetent.FullyExpanded)
	}
}

private fun UnstyledSheetDetent.toYes() = when (this) {
	UnstyledSheetDetent.Hidden        -> SheetDetent.Hidden
	UnstyledSheetDetent.FullyExpanded -> SheetDetent.FullyExpanded
	else                              -> SheetDetent(this)
}

@Immutable
data class ModalSheetProperties(
	val dismissOnBackPress: Boolean = true,
	val dismissOnClickOutside: Boolean = true,
	val offsetForIme: Boolean = true,
)

private fun ModalSheetProperties.toUnstyled() = UnstyledModalSheetProperties(
	dismissOnBackPress = dismissOnBackPress,
	dismissOnClickOutside = dismissOnClickOutside,
	offsetForIme = offsetForIme,
)

@Suppress("unused")
@Stable
class ModalBottomSheetState internal constructor(internal val delegate: UnstyledModalBottomSheetState) {
	val currentDetent get() = delegate.currentDetent.toYes()

	var targetDetent
		get() = delegate.targetDetent.toYes()
		set(value) {
			delegate.targetDetent = value.delegate
		}

	val isIdle get() = delegate.isIdle

	val offset get() = delegate.offset

	fun progress(from: SheetDetent, to: SheetDetent) = delegate.progress(from.delegate, to.delegate)

	suspend fun animateTo(detent: SheetDetent) = delegate.animateTo(detent.delegate)

	fun jumpTo(detent: SheetDetent) = delegate.jumpTo(detent.delegate)

	fun invalidateDetents() = delegate.invalidateDetents()
}

@Stable
class ModalBottomSheetScope internal constructor(internal val delegate: UnstyledModalBottomSheetScope)

@Stable
class ModalBottomSheetPanelScope internal constructor()

@Suppress("unused")
@Composable
fun rememberModalBottomSheetState(
	initialDetent: SheetDetent,
	detents: List<SheetDetent> = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
	animationSpec: AnimationSpec<Float> = tween(),
	dismissAnimationSpec: AnimationSpec<Float>? = null,
	velocityThreshold: () -> Dp = { 125.dp },
	positionalThreshold: (totalDistance: Dp) -> Dp = { 56.dp },
	confirmDetentChange: (SheetDetent) -> Boolean = { true },
	decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
): ModalBottomSheetState {
	val delegate = rememberUnstyledModalBottomSheetState(
		initialDetent = initialDetent.delegate,
		detents = detents.map { it.delegate },
		animationSpec = animationSpec,
		dismissAnimationSpec = dismissAnimationSpec,
		velocityThreshold = velocityThreshold,
		positionalThreshold = positionalThreshold,
		confirmDetentChange = { confirmDetentChange(it.toYes()) },
		decayAnimationSpec = decayAnimationSpec,
	)
	return remember(delegate) { ModalBottomSheetState(delegate) }
}

@Suppress("unused")
@Composable
fun ModalBottomSheet(
	state: ModalBottomSheetState,
	properties: ModalSheetProperties = ModalSheetProperties(),
	onDismiss: () -> Unit = {},
	overlay: (@Composable ModalScope.() -> Unit)? = { ModalBottomSheetScrim() },
	content: @Composable ModalBottomSheetScope.() -> Unit,
) = UnstyledModalBottomSheet(
	state = state.delegate,
	properties = properties.toUnstyled(),
	onDismiss = onDismiss,
	overlay = overlay?.let { overlayContent ->
		{ ModalScope(this).overlayContent() }
	},
	content = { ModalBottomSheetScope(this).content() },
)

@Suppress("unused")
@Composable
fun ModalBottomSheetScope.ModalBottomSheetPanel(
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.sheet,
	backgroundColor: Color = YesTheme.colors.background,
	contentColor: Color = YesTheme.colors.textPrimary,
	contentPadding: PaddingValues = PaddingValues(YesTheme.sizes.spacingExtraLarge),
	content: @Composable ModalBottomSheetPanelScope.() -> Unit,
) = with(delegate) {
	UnstyledModalBottomSheetPanel(
		modifier = modifier,
	) {
		Surface(
			shape = shape,
			backgroundColor = backgroundColor,
			contentColor = contentColor,
			contentPadding = contentPadding,
			content = { ModalBottomSheetPanelScope().content() },
		)
	}
}

@Suppress("unused")
@Composable
fun ModalScope.ModalBottomSheetScrim(
	modifier: Modifier = Modifier,
	scrimColor: Color = YesTheme.colors.backdrop,
	enter: EnterTransition = EnterTransition.None,
	exit: ExitTransition = ExitTransition.None,
) = with(delegate) {
	UnstyledModalBottomSheetScrim(
		modifier = modifier,
		scrimColor = scrimColor,
		enter = enter,
		exit = exit,
	)
}