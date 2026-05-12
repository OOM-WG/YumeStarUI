@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.*
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.DropdownMenuPanel as UnstyledDropdownMenuPanel
import com.composeunstyled.DropdownMenuPanelScope as UnstyledDropdownMenuPanelScope
import com.composeunstyled.DropdownMenuScope as UnstyledDropdownMenuScope
import com.composeunstyled.MenuItem as UnstyledMenuItem

enum class DropdownPanelAnchor { TopStart, TopEnd, BottomStart, BottomEnd, CenterStart, CenterEnd }

private val DropdownPanelAnchor.side
	get() = when (this) {
		DropdownPanelAnchor.TopStart, DropdownPanelAnchor.TopEnd       -> AnchorSide.Top
		DropdownPanelAnchor.BottomStart, DropdownPanelAnchor.BottomEnd -> AnchorSide.Bottom
		DropdownPanelAnchor.CenterStart                                -> AnchorSide.Start
		DropdownPanelAnchor.CenterEnd                                  -> AnchorSide.End
	}

private val DropdownPanelAnchor.alignment
	get() = when (this) {
		DropdownPanelAnchor.TopStart, DropdownPanelAnchor.BottomStart  -> AnchorAlignment.Start
		DropdownPanelAnchor.TopEnd, DropdownPanelAnchor.BottomEnd      -> AnchorAlignment.End
		DropdownPanelAnchor.CenterStart, DropdownPanelAnchor.CenterEnd -> AnchorAlignment.Center
	}

@Stable
class DropdownMenuScope internal constructor(internal val delegate: UnstyledDropdownMenuScope)

@Stable
class DropdownMenuPanelScope internal constructor(
	internal val delegate: UnstyledDropdownMenuPanelScope,
	columnScope: ColumnScope,
) : ColumnScope by columnScope

@Suppress("unused")
@Composable
fun DropdownMenu(
	expanded: Boolean,
	onExpandedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
	anchor: DropdownPanelAnchor = DropdownPanelAnchor.BottomStart,
	sideOffset: Dp = 4.dp,
	alignmentOffset: Dp = 0.dp,
	panel: @Composable DropdownMenuScope.() -> Unit,
	content: @Composable () -> Unit,
) = UnstyledDropdownMenu(
	expanded = expanded,
	onExpandedChange = onExpandedChange,
	modifier = modifier,
	side = anchor.side,
	alignment = anchor.alignment,
	sideOffset = sideOffset,
	alignmentOffset = alignmentOffset,
	panel = { DropdownMenuScope(this).panel() },
	anchor = content,
)

@Suppress("unused")
@Composable
fun DropdownMenuScope.DropdownMenuPanel(
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.medium,
	backgroundColor: Color = YesTheme.colors.background,
	contentColor: Color = YesTheme.colors.textPrimary,
	contentPadding: PaddingValues = PaddingValues(
		horizontal = YesTheme.sizes.spacingLarge,
		vertical = YesTheme.sizes.spacingMedium,
	),
	enter: EnterTransition = defaultDropdownMenuEnterTransition(),
	exit: ExitTransition = defaultDropdownMenuExitTransition(),
	verticalArrangement: Arrangement.Vertical = Arrangement.Top,
	horizontalAlignment: Alignment.Horizontal = Alignment.Start,
	content: @Composable DropdownMenuPanelScope.() -> Unit,
) = with(delegate) {
	UnstyledDropdownMenuPanel(
		modifier = modifier,
		enter = enter,
		exit = exit,
	) {
		val panelDelegate = this
		Surface(
			shape = shape,
			backgroundColor = backgroundColor,
			contentColor = contentColor,
			contentPadding = contentPadding,
		) {
			Column(
				Modifier.wrapContentWidth(),
				verticalArrangement = verticalArrangement,
				horizontalAlignment = horizontalAlignment,
			) { DropdownMenuPanelScope(panelDelegate, this).content() }
		}
	}
}

@Suppress("unused")
@Composable
fun DropdownMenuPanelScope.DropdownMenuItem(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	selected: Boolean = false,
	closeOnClick: Boolean = true,
	interactionSource: MutableInteractionSource? = null,
	indication: Indication? = LocalIndication.current,
	shape: Shape = YesTheme.shapes.medium,
	backgroundColor: Color = if (selected) YesTheme.colors.tint else Color.Transparent,
	contentColor: Color = if (selected) YesTheme.colors.onTint else YesTheme.colors.textPrimary,
	contentPadding: PaddingValues = PaddingValues(
		horizontal = YesTheme.sizes.spacingLarge,
		vertical = YesTheme.sizes.spacingMedium,
	),
	horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
	verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
	content: @Composable RowScope.() -> Unit,
) = with(delegate) {
	UnstyledMenuItem(
		onClick = onClick,
		modifier = modifier.clip(shape),
		enabled = enabled,
		closeOnClick = closeOnClick,
		interactionSource = interactionSource,
		indication = indication,
	) {
		Surface(
			shape = shape,
			backgroundColor = if (enabled) backgroundColor else YesTheme.colors.disabledBackground,
			contentColor = if (enabled) contentColor else YesTheme.colors.textTertiary,
			contentPadding = contentPadding,
		) {
			Row(
				Modifier.fillMaxWidth(),
				horizontalArrangement = horizontalArrangement,
				verticalAlignment = verticalAlignment,
				content = content,
			)
		}
	}
}

private fun defaultDropdownMenuEnterTransition() = fadeIn(
	animationSpec = tween(durationMillis = 90),
) + scaleIn(
	animationSpec = tween(durationMillis = 120),
	initialScale = 0.96f,
)

private fun defaultDropdownMenuExitTransition() = fadeOut(
	animationSpec = tween(durationMillis = 75),
) + scaleOut(
	animationSpec = tween(durationMillis = 90),
	targetScale = 0.98f,
)