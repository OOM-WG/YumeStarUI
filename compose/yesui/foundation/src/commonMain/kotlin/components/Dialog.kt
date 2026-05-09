@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.composeunstyled.*
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.DialogProperties as UnstyledDialogProperties
import com.composeunstyled.DialogState as UnstyledDialogState
import com.composeunstyled.rememberDialogState as rememberUnstyledDialogState

typealias DialogState = UnstyledDialogState
typealias DialogProperties = UnstyledDialogProperties

@Suppress("unused")
@Composable
fun rememberDialogState(initiallyVisible: Boolean = false): DialogState = rememberUnstyledDialogState(initiallyVisible)

@Suppress("unused")
@Composable
fun Dialog(
	state: DialogState,
	properties: DialogProperties = DialogProperties(),
	onDismiss: () -> Unit = {},
	content: @Composable () -> Unit,
) = UnstyledDialog(
	state = state,
	properties = properties,
	onDismiss = onDismiss,
	content = content,
)

@Suppress("unused")
@Composable
fun DialogPanel(
	modifier: Modifier = Modifier,
	enter: EnterTransition = EnterTransition.None,
	exit: ExitTransition = ExitTransition.None,
	shape: Shape = YesTheme.shapes.dialog,
	backgroundColor: Color = YesTheme.colors.background,
	contentColor: Color = YesTheme.colors.textPrimary,
	contentPadding: PaddingValues = PaddingValues(YesTheme.sizes.spacingExtraLarge),
	content: @Composable () -> Unit,
) = UnstyledDialogPanel(
	modifier = modifier,
	enter = enter,
	exit = exit,
	shape = shape,
	backgroundColor = backgroundColor,
	contentColor = contentColor,
	contentPadding = contentPadding,
	content = content,
)

@Suppress("unused")
@Composable
fun DialogScrim(
	modifier: Modifier = Modifier,
	scrimColor: Color = YesTheme.colors.backdrop,
	enter: EnterTransition = EnterTransition.None,
	exit: ExitTransition = ExitTransition.None,
) = UnstyledScrim(
	modifier = modifier,
	scrimColor = scrimColor,
	enter = enter,
	exit = exit,
)