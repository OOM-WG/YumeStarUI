@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.composeunstyled.UnstyledDialog
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.DialogPanel as UnstyledDialogPanel
import com.composeunstyled.DialogProperties as UnstyledDialogProperties
import com.composeunstyled.DialogScope as UnstyledDialogScope
import com.composeunstyled.Scrim as UnstyledScrim

@Stable
class DialogState(initiallyVisible: Boolean = false) {
	var visible by mutableStateOf(initiallyVisible)
}

@Stable
class DialogScope internal constructor(internal val delegate: UnstyledDialogScope)

@Immutable
data class DialogProperties(
	val dismissOnBackPress: Boolean = true,
	val dismissOnClickOutside: Boolean = true,
)

private fun DialogProperties.toUnstyled() = UnstyledDialogProperties(
	dismissOnBackPress = dismissOnBackPress,
	dismissOnClickOutside = dismissOnClickOutside,
)

@Suppress("unused")
@Composable
fun rememberDialogState(initiallyVisible: Boolean = false) = remember { DialogState(initiallyVisible) }

@Suppress("unused")
@Composable
fun Dialog(
	state: DialogState,
	properties: DialogProperties = DialogProperties(),
	onDismiss: () -> Unit = {},
	overlay: (@Composable ModalScope.() -> Unit)? = { DialogScrim() },
	content: @Composable DialogScope.() -> Unit,
) = UnstyledDialog(
	visible = state.visible,
	properties = properties.toUnstyled(),
	onDismissRequest = {
		state.visible = false
		onDismiss()
	},
	overlay = overlay?.let { overlayContent ->
		{ ModalScope(this).overlayContent() }
	},
	content = {
		val dialogScope = DialogScope(this)
		Box(
			Modifier.fillMaxSize(),
			Alignment.Center,
		) { dialogScope.content() }
	},
)

@Suppress("unused")
@Composable
fun DialogScope.DialogPanel(
	modifier: Modifier = Modifier,
	paneTitle: String? = null,
	enter: EnterTransition = defaultDialogPanelEnterTransition(),
	exit: ExitTransition = defaultDialogPanelExitTransition(),
	shape: Shape = YesTheme.shapes.dialog,
	backgroundColor: Color = YesTheme.colors.background,
	contentColor: Color = YesTheme.colors.textPrimary,
	contentPadding: PaddingValues = PaddingValues(YesTheme.sizes.spacingExtraLarge),
	content: @Composable () -> Unit,
) = with(delegate) {
	UnstyledDialogPanel(
		modifier = modifier,
		paneTitle = paneTitle,
		enter = enter,
		exit = exit,
	) {
		Surface(
			shape = shape,
			backgroundColor = backgroundColor,
			contentColor = contentColor,
			contentPadding = contentPadding,
			content = { content() },
		)
	}
}

@Suppress("unused")
@Composable
fun ModalScope.DialogScrim(
	modifier: Modifier = Modifier,
	scrimColor: Color = YesTheme.colors.backdrop,
	enter: EnterTransition = defaultDialogScrimEnterTransition(),
	exit: ExitTransition = defaultDialogScrimExitTransition(),
) = with(delegate) {
	UnstyledScrim(
		modifier = modifier,
		scrimColor = scrimColor,
		enter = enter,
		exit = exit,
	)
}

private fun defaultDialogPanelEnterTransition() = fadeIn(
	animationSpec = tween(durationMillis = 120),
) + scaleIn(
	animationSpec = tween(durationMillis = 180),
	initialScale = 0.94f,
)

private fun defaultDialogPanelExitTransition() = fadeOut(
	animationSpec = tween(durationMillis = 90),
) + scaleOut(
	animationSpec = tween(durationMillis = 120),
	targetScale = 0.98f,
)

private fun defaultDialogScrimEnterTransition() = fadeIn(
	animationSpec = tween(durationMillis = 120),
)

private fun defaultDialogScrimExitTransition() = fadeOut(
	animationSpec = tween(durationMillis = 120),
)