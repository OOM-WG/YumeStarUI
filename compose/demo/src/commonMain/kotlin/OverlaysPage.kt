package work.niggergo.yesui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import work.niggergo.yesui.foundation.components.*
import work.niggergo.yesui.foundation.theme.YesTheme
import work.niggergo.yesui.icons.*

@Composable
internal fun OverlaysPage() {
	val colors = YesTheme.colors
	val sizes = YesTheme.sizes
	val dialogState = rememberDialogState()
	val sheetState = rememberModalBottomSheetState(SheetDetent.Hidden)
	val dockedSheetState = rememberBottomSheetState(SheetDetent.Hidden)
	val scope = rememberCoroutineScope()
	var dropdownExpanded by remember { mutableStateOf(false) }
	var selectedAnchor by remember { mutableStateOf(DropdownPanelAnchor.BottomStart) }
	val dropdownAnchors = listOf(
		DropdownPanelAnchor.BottomStart,
		DropdownPanelAnchor.TopStart,
		DropdownPanelAnchor.CenterEnd,
	)

	Column(
		Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(sizes.spacingExtraLarge),
	) {
		SectionCard(title = "Floating") {
			Row(
				Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(sizes.spacingLarge),
			) {
				Button(
					onClick = { dialogState.visible = true },
					modifier = Modifier.weight(1f),
					backgroundColor = colors.tint,
					contentColor = colors.onTint,
				) {
					Icon(
						YesIcons.MessageSquare,
						size = sizes.iconSmall,
					)
					Spacer(Modifier.width(sizes.spacingSmall))
					Text("Dialog", maxLines = 1)
				}
				Button(
					onClick = { scope.launch { sheetState.animateTo(SheetDetent.FullyExpanded) } },
					modifier = Modifier.weight(1f),
				) {
					Icon(
						YesIcons.PanelBottom,
						size = sizes.iconSmall,
					)
					Spacer(Modifier.width(sizes.spacingSmall))
					Text("Sheet", maxLines = 1)
				}
			}

			DropdownMenu(
				expanded = dropdownExpanded,
				onExpandedChange = { dropdownExpanded = it },
				modifier = Modifier.fillMaxWidth(),
				anchor = selectedAnchor,
				panel = {
					DropdownMenuPanel(
						modifier = Modifier.width(220.dp),
						verticalArrangement = Arrangement.spacedBy(sizes.spacingSmall),
					) {
						dropdownAnchors.forEach { option ->
							DropdownMenuItem(
								selected = selectedAnchor == option,
								onClick = {
									selectedAnchor = option
									dropdownExpanded = false
								},
							) {
								Text(option.label(), maxLines = 1, overflow = TextOverflow.Ellipsis)
							}
						}
					}
				},
			) {
				Button(
					onClick = { dropdownExpanded = true },
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
				) {
					Row(
						horizontalArrangement = Arrangement.spacedBy(sizes.spacingSmall),
						verticalAlignment = Alignment.CenterVertically,
					) {
						Icon(YesIcons.SquareMousePointer, size = sizes.iconSmall)
						Text(selectedAnchor.label(), maxLines = 1)
					}
					Icon(YesIcons.ChevronDown, size = sizes.iconSmall)
				}
			}

			Tooltip(
				panel = {
					TooltipPanel { _ ->
						Text(
							"Tooltip panel uses the same rounded surface styling.",
							style = YesTheme.typography.caption,
							color = colors.textPrimary,
						)
					}
				},
			) {
				Button(
					onClick = {},
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Start,
				) {
					Icon(YesIcons.Info, size = sizes.iconSmall)
					Spacer(Modifier.width(sizes.spacingSmall))
					Text("Hover or long press for tooltip", maxLines = 1)
				}
			}
		}

		SectionCard(title = "Bottom Sheet") {
			Row(
				Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(sizes.spacingLarge),
			) {
				Button(
					onClick = { scope.launch { dockedSheetState.animateTo(SheetDetent.FullyExpanded) } },
					modifier = Modifier.weight(1f),
				) {
					Text("Expand", maxLines = 1)
				}
				Button(
					onClick = { scope.launch { dockedSheetState.animateTo(SheetDetent.Hidden) } },
					modifier = Modifier.weight(1f),
				) { Text("Hide", maxLines = 1) }
			}
			Box(Modifier.fillMaxWidth().height(190.dp).background(colors.backgroundVariant, YesTheme.shapes.large)) {
				Text(
					"Bounded sheet area",
					Modifier.padding(sizes.spacingLarge),
					style = YesTheme.typography.bodySmall,
					color = colors.textTertiary,
				)
				BottomSheet(state = dockedSheetState, modifier = Modifier.matchParentSize()) {
					BottomSheetPanel(modifier = Modifier.fillMaxWidth()) {
						val panel = this
						Column(
							Modifier.fillMaxWidth(),
							verticalArrangement = Arrangement.spacedBy(sizes.spacingMedium),
						) {
							panel.BottomSheetDragHandle(modifier = Modifier.align(Alignment.CenterHorizontally))
							Text(
								"Non-modal Bottom Sheet",
								style = YesTheme.typography.subtitle,
								color = colors.title,
								fontWeight = FontWeight.SemiBold,
							)
							Text(
								"The plain BottomSheet primitive stays inside its parent bounds.",
								style = YesTheme.typography.bodySmall,
								color = colors.textSecondary,
							)
						}
					}
				}
			}
		}
	}

	Dialog(
		state = dialogState, onDismiss = { dialogState.visible = false },
	) {
		DialogPanel(modifier = Modifier.widthIn(min = 280.dp, max = 420.dp)) {
			Column(
				Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(sizes.spacingLarge),
			) {
				Text(
					"Dialog",
					style = YesTheme.typography.subtitle,
					color = colors.title,
					fontWeight = FontWeight.SemiBold,
				)
				Text(
					"Dialog state and platform details stay inside the foundation wrapper.",
					style = YesTheme.typography.bodySmall,
					color = colors.textSecondary,
				)
				Row(
					Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.End,
				) {
					Button(
						onClick = { dialogState.visible = false },
						backgroundColor = colors.tint,
						contentColor = colors.onTint,
					) { Text("Close", maxLines = 1) }
				}
			}
		}
	}

	ModalBottomSheet(
		state = sheetState,
		onDismiss = { scope.launch { sheetState.animateTo(SheetDetent.Hidden) } },
	) {
		ModalBottomSheetPanel(modifier = Modifier.fillMaxWidth().heightIn(max = 420.dp)) {
			Column(
				Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(sizes.spacingLarge),
			) {
				Box(
					Modifier.width(44.dp).height(4.dp).background(colors.divider, YesTheme.shapes.pill)
						.align(Alignment.CenterHorizontally),
				)
				Text(
					"Modal Bottom Sheet",
					style = YesTheme.typography.subtitle,
					color = colors.title,
					fontWeight = FontWeight.SemiBold,
				)
				Text(
					"The sheet shares the same theme tokens as the rest of the foundation layer.",
					style = YesTheme.typography.bodySmall,
					color = colors.textSecondary,
				)
				Button(
					onClick = { scope.launch { sheetState.animateTo(SheetDetent.Hidden) } },
					modifier = Modifier.fillMaxWidth(),
				) { Text("Done", maxLines = 1) }
			}
		}
	}
}

private fun DropdownPanelAnchor.label() = when (this) {
	DropdownPanelAnchor.TopStart -> "Top start"
	DropdownPanelAnchor.TopEnd -> "Top end"
	DropdownPanelAnchor.BottomStart -> "Bottom start"
	DropdownPanelAnchor.BottomEnd -> "Bottom end"
	DropdownPanelAnchor.CenterStart -> "Center start"
	DropdownPanelAnchor.CenterEnd -> "Center end"
}