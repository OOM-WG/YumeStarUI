package work.niggergo.yesui.demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.foundation.components.*
import work.niggergo.yesui.foundation.theme.YesTheme
import work.niggergo.yesui.icons.*

@Composable
internal fun ControlsPage() {
	var switchChecked by remember { mutableStateOf(true) }
	var checkBoxChecked by remember { mutableStateOf(true) }
	var triState by remember { mutableStateOf(ToggleableState.Indeterminate) }
	var radioValue by remember { mutableStateOf("comfortable") }
	var progress by remember { mutableFloatStateOf(0.64f) }
	val noteState = rememberTextFieldState(initialText = "TextField uses the shared foundation styling.")
	val sliderState = rememberSliderState(initialValue = 0.56f)
	val colors = YesTheme.colors

	Column(
		Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingExtraLarge),
	) {
		SectionCard(title = "Buttons") {
			Row(
				Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingLarge),
			) {
				Button(
					onClick = {},
					modifier = Modifier.weight(1f),
					backgroundColor = colors.tint,
					contentColor = colors.onTint,
				) { Text("Primary", maxLines = 1) }
				Button(
					onClick = {},
					modifier = Modifier.weight(1f),
				) { Text("Default", maxLines = 1) }
			}
			Row(
				Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingLarge),
			) {
				Button(
					onClick = {},
					modifier = Modifier.weight(1f),
					backgroundColor = Color.Transparent,
					contentColor = colors.tint,
					borderColor = colors.tint,
					borderWidth = 1.dp,
				) { Text("Border", maxLines = 1) }
				Button(
					onClick = {},
					modifier = Modifier.weight(1f),
					enabled = false,
				) { Text("Disabled", maxLines = 1) }
			}
		}

		SectionCard(title = "Input") {
			TextField(
				state = noteState,
				modifier = Modifier.fillMaxWidth(),
				minLines = 3,
				maxLines = 4,
			) {
				TextInput(
					modifier = Modifier.fillMaxWidth(),
					leading = {
						Icon(
							YesIcons.TextCursorInput,
							size = YesTheme.sizes.iconSmall,
							tint = colors.textSecondary,
						)
					},
					placeholder = {
						Text(
							"Write a short note",
							style = YesTheme.typography.body,
							color = colors.textTertiary,
						)
					},
				)
			}
		}

		SectionCard(title = "Selection") {
			ControlRow(title = "Switch") {
				ToggleSwitch(
					toggled = switchChecked,
					onToggled = { switchChecked = it },
				)
			}
			ControlRow(title = "Checkbox") {
				CheckBox(
					checked = checkBoxChecked,
					onCheckedChange = { checkBoxChecked = it },
				)
			}
			ControlRow(title = "Tri-state") {
				TriStateCheckBox(
					value = triState,
					onClick = {
						triState = when (triState) {
							ToggleableState.Off           -> ToggleableState.On
							ToggleableState.On            -> ToggleableState.Indeterminate
							ToggleableState.Indeterminate -> ToggleableState.Off
						}
					},
				)
			}
		}

		SectionCard(title = "Range") {
			ControlRow(title = "Slider") {
				Badge(text = "${(sliderState.value * 100).toInt()}%")
			}
			Slider(
				state = sliderState,
				modifier = Modifier.fillMaxWidth().height(32.dp),
			)
			ProgressIndicator(
				progress = sliderState.value,
				modifier = Modifier.fillMaxWidth().height(8.dp),
			)
			ProgressIndicator(
				modifier = Modifier.fillMaxWidth().height(8.dp),
			)
		}

		SectionCard(title = "Progress") {
			ControlRow(title = "${(progress * 100).toInt()}%") {
				IconButton(
					onClick = { progress = (progress - 0.12f).coerceAtLeast(0f) },
					enabled = progress > 0f,
					size = 34.dp,
					backgroundColor = colors.backgroundVariant,
				) {
					Icon(
						YesIcons.Minus,
						size = YesTheme.sizes.iconSmall,
					)
				}
				IconButton(
					onClick = { progress = (progress + 0.12f).coerceAtMost(1f) },
					enabled = progress < 1f,
					size = 34.dp,
					backgroundColor = colors.backgroundVariant,
				) {
					Icon(
						YesIcons.Plus,
						size = YesTheme.sizes.iconSmall,
					)
				}
			}
			ProgressIndicator(
				progress = progress,
				modifier = Modifier.fillMaxWidth().height(8.dp),
			)
		}

		SectionCard(title = "Density") {
			RadioGroup(
				value = radioValue,
				onValueChange = { radioValue = it },
				contentDescription = "Density",
				modifier = Modifier.fillMaxWidth(),
			) {
				RadioButton(value = "compact") { Text("Compact", maxLines = 1) }
				RadioButton(value = "comfortable") { Text("Comfortable", maxLines = 1) }
			}
		}
	}
}