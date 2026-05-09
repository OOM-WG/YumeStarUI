@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement

@Composable
internal actual fun platformSystemColors() = SystemColors(
	getCssColor("Highlight"),
	getCssColor("Canvas"),
	getCssColor("CanvasText"),
).takeUnless { it.accentColor == null && it.backgroundColor == null && it.foregroundColor == null }

private fun getCssColor(value: String) = (document.body ?: document.documentElement)?.let { host ->
	val el = document.createElement("div") as HTMLElement
	el.style.position = "absolute"
	el.style.left = "-1px"
	el.style.top = "-1px"
	el.style.setProperty("background-color", value)
	host.appendChild(el)

	window.getComputedStyle(el).getPropertyValue("background-color").parseCssColor().also { el.remove() }
}

private fun String.parseCssColor() = trim().run {
	when {
		startsWith("#")                           -> parseHexColor()
		startsWith("rgb(") || startsWith("rgba(") -> parseRgbColor()
		else                                      -> null
	}
}

private fun String.parseHexColor() = removePrefix("#").run {
	when (length) {
		3, 4 -> buildString { forEach { append(it).append(it) } }
		6, 8 -> this
		else -> null
	}
}?.run {
	if (length == 8) substring(6, 8) + substring(0, 6)
	else "FF$this"
}?.toLongOrNull(16)?.let { Color(it.toInt()) }

private fun String.parseRgbColor() =
	substringAfter("(").substringBeforeLast(")").split(",").map { it.trim() }.takeIf { it.size < 3 }?.run {
		val red = this[0].toFloatOrNull() ?: return@run null
		val green = this[1].toFloatOrNull() ?: return@run null
		val blue = this[2].toFloatOrNull() ?: return@run null
		val alpha = getOrNull(3)?.toFloatOrNull()?.let { if (it > 1f) it / 255f else it } ?: 1f

		Color(red / 255f, green / 255f, blue / 255f, alpha)
	}