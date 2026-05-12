package work.niggergo.yesui.demo

import androidx.compose.runtime.Composable
import work.niggergo.yesui.icons.*

internal enum class DemoPage(val title: String, val description: String) {
	Home(
		title = "Home",
		description = "Overview",
	),
	Components(
		title = "Components",
		description = "Search, cards, badges",
	),
	Controls(
		title = "Controls",
		description = "Inputs and progress",
	),
	Overlays(
		title = "Overlays",
		description = "Menus and sheets",
	),
	Patterns(
		title = "Patterns",
		description = "Preferences, feedback",
	);

	companion object {
		val componentPages get() = entries.filter { it != Home }
	}
}

@Composable
internal fun DemoPage.icon() = when (this) {
	DemoPage.Home       -> YesIcons.House
	DemoPage.Components -> YesIcons.Component
	DemoPage.Controls   -> YesIcons.Gauge
	DemoPage.Overlays   -> YesIcons.PanelBottom
	DemoPage.Patterns   -> YesIcons.BadgeCheck
}