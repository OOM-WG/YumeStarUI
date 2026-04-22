@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.core.theme.YesTheme
import work.niggergo.yesui.core.utils.YesShapeSize
import work.niggergo.yesui.core.utils.smoothShape
import com.composables.core.BottomSheetScope as UnstyledBottomSheetScope
import com.composables.core.ModalBottomSheet as UnstyledModalBottomSheet
import com.composables.core.ModalBottomSheetScope as UnstyledModalBottomSheetScope
import com.composables.core.ModalBottomSheetState as UnstyledModalBottomSheetState
import com.composables.core.ModalSheetProperties as UnstyledModalSheetProperties
import com.composables.core.Scrim as UnstyledModalBottomSheetScrim
import com.composables.core.Sheet as UnstyledModalBottomSheetPanel
import com.composables.core.SheetDetent as UnstyledSheetDetent
import com.composables.core.rememberModalBottomSheetState as rememberUnstyledModalBottomSheetState

typealias ModalBottomSheetState = UnstyledModalBottomSheetState
typealias ModalBottomSheetScope = UnstyledModalBottomSheetScope
typealias BottomSheetScope = UnstyledBottomSheetScope
typealias ModalSheetProperties = UnstyledModalSheetProperties
typealias SheetDetent = UnstyledSheetDetent

@Suppress("unused")
@Composable
fun rememberModalBottomSheetState(
	initialDetent: SheetDetent,
	detents: List<SheetDetent> = listOf(SheetDetent.Hidden, SheetDetent.FullyExpanded),
	animationSpec: AnimationSpec<Float> = tween(),
	velocityThreshold: () -> Dp = { 125.dp },
	positionalThreshold: (totalDistance: Dp) -> Dp = { 56.dp },
	confirmDetentChange: (SheetDetent) -> Boolean = { true },
	decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
): ModalBottomSheetState = rememberUnstyledModalBottomSheetState(
	initialDetent = initialDetent,
	detents = detents,
	animationSpec = animationSpec,
	velocityThreshold = velocityThreshold,
	positionalThreshold = positionalThreshold,
	confirmDetentChange = confirmDetentChange,
	decayAnimationSpec = decayAnimationSpec,
)

@Suppress("unused")
@Composable
fun ModalBottomSheet(
	state: ModalBottomSheetState,
	properties: ModalSheetProperties = ModalSheetProperties(),
	onDismiss: () -> Unit = {},
	content: @Composable ModalBottomSheetScope.() -> Unit,
) = UnstyledModalBottomSheet(
	state = state,
	properties = properties,
	onDismiss = onDismiss,
	content = content,
)

@Suppress("unused")
@Composable
fun ModalBottomSheetScope.ModalBottomSheetPanel(
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	shape: Shape = smoothShape(YesShapeSize.Large),
	backgroundColor: Color = YesTheme.colors.container,
	contentColor: Color = YesTheme.colors.content,
	contentPadding: PaddingValues = PaddingValues(YesTheme.sizes.spacingPrimary),
	imeAware: Boolean = false,
	content: @Composable BottomSheetScope.() -> Unit,
) = UnstyledModalBottomSheetPanel(
	modifier = modifier,
	enabled = enabled,
	shape = shape,
	backgroundColor = backgroundColor,
	contentColor = contentColor,
	contentPadding = contentPadding,
	imeAware = imeAware,
	content = content,
)

@Suppress("unused")
@Composable
fun ModalBottomSheetScope.ModalBottomSheetScrim(
	modifier: Modifier = Modifier,
	scrimColor: Color = YesTheme.colors.scrim,
	enter: EnterTransition = EnterTransition.None,
	exit: ExitTransition = ExitTransition.None,
) = UnstyledModalBottomSheetScrim(
	modifier = modifier,
	scrimColor = scrimColor,
	enter = enter,
	exit = exit,
)