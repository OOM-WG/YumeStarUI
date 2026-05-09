@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Composable
internal actual fun platformSystemColors() = runCatching { JavaFxSystemThemeProvider.get() }.getOrNull()

private object JavaFxSystemThemeProvider {
	fun get() = onFxThread {
		val preferences = platformClass.getMethod("getPreferences").invoke(null) ?: return@onFxThread null
		SystemColors(
			preferences.invokeColorGetter("getAccentColor"),
			preferences.invokeColorGetter("getBackgroundColor"),
			preferences.invokeColorGetter("getForegroundColor"),
		)
	}

	inline fun <T> onFxThread(crossinline block: () -> T): T {
		if (platformClass.getMethod("isFxApplicationThread").invoke(null) == true) return block()

		val resultLatch = CountDownLatch(1)
		var result = null as Result<T>?

		try {
			platformClass.getMethod("startup", Runnable::class.java).invoke(
				null,
				Runnable {
					result = runCatching(block)
					resultLatch.countDown()
				},
			)
		} catch (exception: InvocationTargetException) {
			if (exception.targetException !is IllegalStateException) throw exception.targetException
		}

		if (result == null) platformClass.getMethod("runLater", Runnable::class.java).invoke(
			null,
			Runnable {
				result = runCatching(block)
				resultLatch.countDown()
			},
		)

		check(resultLatch.await(5, TimeUnit.SECONDS)) { }
		return result!!.getOrThrow()
	}

	private val platformClass by lazy(LazyThreadSafetyMode.NONE) {
		Class.forName("javafx.application.Platform")
	}
}

private fun Any.invokeColorGetter(name: String): Color? = runCatching {
	javaClass.getMethod(name).invoke(this)
}.getOrNull()?.toComposeColor()

private fun Any.toComposeColor(): Color? = runCatching {
	Color(
		(javaClass.getMethod("getRed").invoke(this) as Double).toFloat(),
		(javaClass.getMethod("getGreen").invoke(this) as Double).toFloat(),
		(javaClass.getMethod("getBlue").invoke(this) as Double).toFloat(),
		(javaClass.getMethod("getOpacity").invoke(this) as Double).toFloat(),
	)
}.getOrNull()