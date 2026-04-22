@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.composeunstyled.*
import work.niggergo.yesui.core.theme.YesTheme
import work.niggergo.yesui.core.utils.YesShapeSize
import work.niggergo.yesui.core.utils.smoothShape

@Suppress("unused")
@Composable
fun DropdownMenu(
	onExpandRequest: () -> Unit,
	modifier: Modifier = Modifier,
	content: @Composable () -> Unit,
) = UnstyledDropdownMenu(
	onExpandRequest = onExpandRequest,
	modifier = modifier,
	content = content,
)

@Suppress("unused")
@Composable
fun DropdownMenuPanel(
	expanded: Boolean,
	onDismissRequest: () -> Unit,
	modifier: Modifier = Modifier,
	anchor: DropdownPanelAnchor = DropdownPanelAnchor.BottomStart,
	shape: Shape = smoothShape(YesShapeSize.Small),
	backgroundColor: Color = YesTheme.colors.container,
	contentColor: Color = YesTheme.colors.content,
	contentPadding: PaddingValues = PaddingValues(
		horizontal = YesTheme.sizes.spacingSecondary,
		vertical = YesTheme.sizes.spacingTertiary,
	),
	enter: EnterTransition = EnterTransition.None,
	exit: ExitTransition = ExitTransition.None,
	verticalArrangement: Arrangement.Vertical = Arrangement.Top,
	horizontalAlignment: Alignment.Horizontal = Alignment.Start,
	content: @Composable ColumnScope.() -> Unit,
) = UnstyledDropdownMenuPanel(
	expanded = expanded,
	onDismissRequest = onDismissRequest,
	modifier = modifier,
	anchor = anchor,
	shape = shape,
	backgroundColor = backgroundColor,
	contentColor = contentColor,
	contentPadding = contentPadding,
	enter = enter,
	exit = exit,
	verticalArrangement = verticalArrangement,
	horizontalAlignment = horizontalAlignment,
	content = content,
)