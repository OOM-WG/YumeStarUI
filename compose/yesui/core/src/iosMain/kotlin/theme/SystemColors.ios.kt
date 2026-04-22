@file:OptIn(ExperimentalForeignApi::class) @file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.*
import platform.UIKit.*

@Composable
internal actual fun platformSystemColors() = runCatching {
	SystemColors(
		UIView().tintColor.toComposeColor(),
		UIColor.systemBackgroundColor.toComposeColor(),
		UIColor.labelColor.toComposeColor(),
	).takeUnless { it.accentColor == null && it.backgroundColor == null && it.foregroundColor == null }
}.getOrNull()

private fun UIColor.toComposeColor() = memScoped {
	val red = alloc<DoubleVar>()
	val green = alloc<DoubleVar>()
	val blue = alloc<DoubleVar>()
	val alpha = alloc<DoubleVar>()

	if (!getRed(red.ptr, green.ptr, blue.ptr, alpha.ptr)) return@memScoped null

	Color(
		red.ptr.pointed.value.toFloat(),
		green.ptr.pointed.value.toFloat(),
		blue.ptr.pointed.value.toFloat(),
		alpha.ptr.pointed.value.toFloat(),
	)
}
