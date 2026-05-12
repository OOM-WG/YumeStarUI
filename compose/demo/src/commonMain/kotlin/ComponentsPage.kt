package work.niggergo.yesui.demo

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.components.common.*
import work.niggergo.yesui.foundation.components.*
import work.niggergo.yesui.foundation.theme.YesTheme
import work.niggergo.yesui.icons.*

@Composable
internal fun ComponentsPage() {
	val searchState = rememberTextFieldState()
	val searchFocusRequester = remember { FocusRequester() }
	val pageFocusRequester = remember { FocusRequester() }

	LaunchedEffect(Unit) { pageFocusRequester.requestFocus() }

	Column(
		Modifier.fillMaxWidth().focusRequester(pageFocusRequester).focusable().focusOnUnconsumedTap(pageFocusRequester)
			.searchBoxFocusShortcut(focusRequester = searchFocusRequester),
		verticalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingExtraLarge),
	) {
		SearchBox(
			state = searchState,
			modifier = Modifier.fillMaxWidth(),
			focusRequester = searchFocusRequester,
			placeholder = "Search components...",
		)

		CardItemsShowcase()
		BadgeShowcaseCard()
		ContainerShowcaseCard()
		ScrollbarShowcaseCard()
	}
}

@Composable
private fun CardItemsShowcase() {
	Column(
		Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingLarge),
	) {
		CardItem(
			title = "Card item",
			description = "Common row layout.",
			painter = YesIcons.Settings2,
			onClick = {},
			endContent = {
				Badge(text = "New")
			},
		)
		CardItem(
			title = "Disabled item",
			description = "Lower emphasis.",
			painter = YesIcons.Lock,
			enabled = false,
			endContent = {
				Badge(
					text = "Off",
					enabled = false,
				)
			},
		)
		CardItem(
			title = "Value item",
			description = "Trailing status.",
			painter = YesIcons.Palette,
			endContent = {
				Text(
					"Tint",
					style = YesTheme.typography.label,
					color = YesTheme.colors.tint,
					maxLines = 1,
				)
			},
		)
	}
}

@Composable
private fun BadgeShowcaseCard() = SectionCard(title = "Badges") {
	BadgeRow(
		badges = listOf(
			BadgeData(text = "Theme"),
			BadgeData(text = "Ready", painter = YesIcons.CircleCheck, color = Color(0xFF22C55E)),
			BadgeData(text = "Soon", painter = YesIcons.Clock, color = Color(0xFFF59E0B)),
			BadgeData(text = "Blocked", painter = YesIcons.Ban, color = Color(0xFFEF4444)),
		),
	)
	BadgeRow(
		badges = listOf(
			BadgeData(text = "Border", border = true),
			BadgeData(text = "Check", painter = YesIcons.CircleCheck, color = Color(0xFF22C55E), border = true),
			BadgeData(text = "Clock", painter = YesIcons.Clock, color = Color(0xFFF59E0B), border = true),
			BadgeData(text = "Disabled", painter = YesIcons.Ban, color = Color(0xFFEF4444), enabled = false),
		),
	)
}

@Composable
private fun ContainerShowcaseCard() {
	var disclosureExpanded by remember { mutableStateOf(false) }
	var selectedTab by remember { mutableStateOf("Disclosure") }
	val tabs = listOf("Disclosure", "Tabs", "Wallpaper")
	val colors = YesTheme.colors
	val sizes = YesTheme.sizes

	SectionCard(title = "Containers") {
		Disclosure(
			expanded = disclosureExpanded,
			onExpandedChange = { disclosureExpanded = it },
			modifier = Modifier.fillMaxWidth(),
		) {
			DisclosureButton(modifier = Modifier.fillMaxWidth().clip(YesTheme.shapes.medium)) {
				Surface(
					modifier = Modifier.fillMaxWidth(),
					shape = YesTheme.shapes.medium,
					backgroundColor = colors.backgroundVariant,
					contentPadding = PaddingValues(
						horizontal = sizes.spacingLarge,
						vertical = sizes.spacingMedium,
					),
				) {
					Row(
						Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically,
					) {
						Text(
							"Disclosure",
							style = YesTheme.typography.body,
							color = colors.textPrimary,
							maxLines = 1,
						)
						Icon(
							if (expanded) YesIcons.ChevronUp else YesIcons.ChevronDown,
							size = sizes.iconSmall,
							tint = colors.textSecondary,
						)
					}
				}
			}
			DisclosedContent {
				Text(
					"Expandable content keeps layout and theme styling in YesUI.",
					style = YesTheme.typography.bodySmall,
					color = colors.textSecondary,
				)
			}
		}

		TabGroup(
			selectedTab = selectedTab,
			onSelectedTabChange = { selectedTab = it },
			tabs = tabs,
			modifier = Modifier.fillMaxWidth(),
		) {
			TabList(modifier = Modifier.fillMaxWidth()) {
				Row(
					Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(sizes.spacingSmall),
				) {
					tabs.forEach { tab ->
						Tab(
							key = tab,
							modifier = Modifier.weight(1f).clip(YesTheme.shapes.medium),
						) {
							Surface(
								modifier = Modifier.fillMaxWidth(),
								shape = YesTheme.shapes.medium,
								backgroundColor = if (selected) colors.tint else colors.backgroundVariant,
								contentColor = if (selected) colors.onTint else colors.textPrimary,
								contentPadding = PaddingValues(
									horizontal = sizes.spacingMedium,
									vertical = sizes.spacingSmall,
								),
							) {
								Box(Modifier.fillMaxWidth(), Alignment.Center) {
									Text(tab, maxLines = 1, overflow = TextOverflow.Ellipsis)
								}
							}
						}
					}
				}
			}
			tabs.forEach { tab ->
				TabPanel(
					key = tab,
					modifier = Modifier.fillMaxWidth().padding(top = sizes.spacingMedium),
				) {
					Text(
						"$tab content area.",
						style = YesTheme.typography.bodySmall,
						color = colors.textSecondary,
					)
				}
			}
		}

		BackgroundImageCard(
			modifier = Modifier.fillMaxWidth().height(180.dp),
			gradientColor = colors.surface,
		) {
			Text(
				"Background image card",
				Modifier.align(Alignment.BottomStart),
				style = YesTheme.typography.body,
				color = colors.textPrimary,
			)
		}
	}
}

@Composable
private fun ScrollbarShowcaseCard() {
	val colors = YesTheme.colors
	val sizes = YesTheme.sizes
	val scrollState = rememberScrollState()
	val scrollbarState = rememberScrollbarState(scrollState)

	SectionCard(title = "Scrollbars") {
		Box(Modifier.fillMaxWidth().height(160.dp).background(colors.backgroundVariant, YesTheme.shapes.large)) {
			Column(
				Modifier.fillMaxSize().verticalScroll(scrollState).padding(
					start = sizes.spacingLarge,
					top = sizes.spacingLarge,
					end = sizes.spacingExtraLarge,
					bottom = sizes.spacingLarge,
				),
				verticalArrangement = Arrangement.spacedBy(sizes.spacingMedium),
			) {
				repeat(10) { index ->
					Text(
						"Scrollable row ${index + 1}",
						style = YesTheme.typography.bodySmall,
						color = colors.textPrimary,
					)
				}
			}
			VerticalScrollbar(
				state = scrollbarState,
				modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight().padding(
					horizontal = sizes.spacingSmall,
					vertical = sizes.spacingMedium,
				),
			)
		}
	}
}

private data class BadgeData(
	val text: String,
	val painter: Painter? = null,
	val color: Color = Color.Unspecified,
	val border: Boolean = false,
	val enabled: Boolean = true,
)

@Composable
private fun BadgeRow(badges: List<BadgeData>, modifier: Modifier = Modifier) = Row(
	modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
	horizontalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingSmall),
	verticalAlignment = Alignment.CenterVertically,
) {
	badges.forEach { item ->
		if (item.painter == null) Badge(
			text = item.text,
			color = item.color,
			border = item.border,
			enabled = item.enabled,
		)
		else Badge(
			text = item.text,
			painter = item.painter,
			color = item.color,
			border = item.border,
			enabled = item.enabled,
		)
	}
}

private fun Modifier.focusOnUnconsumedTap(focusRequester: FocusRequester) = pointerInput(focusRequester) {
	awaitEachGesture {
		val down = awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Final)
		val up = waitForUpOrCancellation(pass = PointerEventPass.Final)
		if (!down.isConsumed && up != null && !up.isConsumed) focusRequester.requestFocus()
	}
}