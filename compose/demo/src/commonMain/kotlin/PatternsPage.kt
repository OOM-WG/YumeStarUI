package work.niggergo.yesui.demo

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import work.niggergo.yesui.foundation.components.Icon
import work.niggergo.yesui.foundation.components.ToggleSwitch
import work.niggergo.yesui.foundation.theme.YesTheme
import work.niggergo.yesui.icons.*
import work.niggergo.yesui.patterns.feedback.*
import work.niggergo.yesui.patterns.preference.*

@Composable
internal fun PatternsPage() {
	var notifications by remember { mutableStateOf(true) }
	var darkMode by remember { mutableStateOf(false) }
	var updates by remember { mutableStateOf(ToggleableState.On) }

	Column(
		Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingExtraLarge),
	) {
		Column(
			Modifier.fillMaxWidth(),
			verticalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingLarge),
		) {
			SwitchPreferenceCard(
				title = "Notifications",
				description = "Activity changes.",
				checked = notifications,
				onCheckedChange = { notifications = it },
				icon = { Icon(YesIcons.Bell) },
			)
			SwitchPreferenceCard(
				title = "Dark mode",
				description = "Palette preview.",
				checked = darkMode,
				onCheckedChange = { darkMode = it },
				icon = { Icon(painter = YesIcons.Moon) },
			)
			CheckboxPreferenceCard(
				title = "Auto update",
				description = "Tri-state option.",
				value = updates,
				onClick = {
					updates = when (updates) {
						ToggleableState.Off           -> ToggleableState.On
						ToggleableState.On            -> ToggleableState.Indeterminate
						ToggleableState.Indeterminate -> ToggleableState.Off
					}
				},
				icon = { Icon(YesIcons.Download) },
			)
			PreferenceCard(
				title = "Wi-Fi only",
				description = "Plain preference row.",
				icon = { Icon(YesIcons.Wifi) },
				endContent = {
					ToggleSwitch(
						toggled = darkMode,
						onToggled = { darkMode = it },
					)
				},
			)
			PreferenceCard(
				title = "Locked option",
				description = "Disabled state.",
				enabled = false,
				icon = { Icon(YesIcons.Lock) },
				endContent = {
					ToggleSwitch(
						toggled = true,
						onToggled = null,
						enabled = false,
					)
				},
			)
		}

		SectionCard(title = "Annotations") {
			InfoBox(text = "Theme tint is used when no explicit color is supplied.")
			WarningBox(text = "Warning colors are mixed into opaque theme surfaces.")
			TipBox(text = "Pattern components can use the full icon set directly.")
			DangerBox(text = "Disabled or destructive states keep contrast.")
		}
	}
}