package work.niggergo.yesui.demo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
	Window(
		onCloseRequest = ::exitApplication,
		title = "YumeStarUI Demo",
	) { AppTheme { App() } }
}