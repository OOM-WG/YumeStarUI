@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.KeyEvent
import com.composeunstyled.Modal as UnstyledModal

@Suppress("unused")
@Composable
fun Modal(
	onKeyEvent: (KeyEvent) -> Boolean = { false },
	content: @Composable () -> Unit,
) = UnstyledModal(
	onKeyEvent = onKeyEvent,
	content = content,
)