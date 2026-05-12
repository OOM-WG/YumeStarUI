@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.input.key.KeyEvent
import com.composeunstyled.Modal as UnstyledModal
import com.composeunstyled.ModalScope as UnstyledModalScope
import com.composeunstyled.ModalState as UnstyledModalState
import com.composeunstyled.rememberModalState as rememberUnstyledModalState

@Suppress("unused")
@Stable
class ModalState internal constructor(internal val delegate: UnstyledModalState) {
	constructor(initiallyVisible: Boolean = false) : this(UnstyledModalState(initiallyVisible))
}

@Stable
class ModalScope internal constructor(internal val delegate: UnstyledModalScope)

@Suppress("unused")
@Composable
fun rememberModalState(initiallyVisible: Boolean = false): ModalState {
	val delegate = rememberUnstyledModalState(initiallyVisible)
	return androidx.compose.runtime.remember(delegate) { ModalState(delegate) }
}

@Suppress("unused")
@Composable
fun Modal(
	state: ModalState = rememberModalState(initiallyVisible = true),
	onKeyEvent: (KeyEvent) -> Boolean = { false },
	content: @Composable ModalScope.() -> Unit,
) = UnstyledModal(
	state = state.delegate,
	onKeyEvent = onKeyEvent,
	content = { ModalScope(this).content() },
)