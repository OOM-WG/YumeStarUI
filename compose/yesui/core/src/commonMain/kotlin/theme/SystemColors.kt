@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

internal val JavaFXDefaultAccentColor = Color(0xFF157EFB)

@Immutable
internal data class SystemColors(
	val accentColor: Color? = null,
	val backgroundColor: Color? = null,
	val foregroundColor: Color? = null,
)

@Composable
internal fun getSystemColors() =
	(platformSystemColors() ?: SystemColors()).run { copy(accentColor = accentColor ?: JavaFXDefaultAccentColor) }

@Composable
internal expect fun platformSystemColors(): SystemColors?