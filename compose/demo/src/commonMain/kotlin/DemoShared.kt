package work.niggergo.yesui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.foundation.components.*
import work.niggergo.yesui.foundation.theme.YesTheme
import work.niggergo.yesui.icons.*

@Composable
internal fun ComponentDemoPage(page: DemoPage, onPageChange: (DemoPage) -> Unit) = Column(
	Modifier.fillMaxWidth(),
	verticalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingExtraLarge),
) {
	ComponentPageSelector(
		page = page,
		onPageChange = onPageChange,
	)

	when (page) {
		DemoPage.Home       -> Unit
		DemoPage.Components -> ComponentsPage()
		DemoPage.Controls   -> ControlsPage()
		DemoPage.Overlays   -> OverlaysPage()
		DemoPage.Patterns   -> PatternsPage()
	}
}

@Composable
private fun ComponentPageSelector(page: DemoPage, onPageChange: (DemoPage) -> Unit) {
	val pages = DemoPage.componentPages
	val currentIndex = pages.indexOf(page).coerceAtLeast(0)
	val colors = YesTheme.colors
	val sizes = YesTheme.sizes

	Card(
		modifier = Modifier.fillMaxWidth(),
		backgroundColor = colors.background,
	) {
		Row(
			Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(sizes.spacingExtraLarge),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Column(
				Modifier.weight(1f),
				verticalArrangement = Arrangement.spacedBy(sizes.spacingSmall),
			) {
				Text(
					page.title,
					style = YesTheme.typography.subtitle,
					color = colors.textPrimary,
					fontWeight = FontWeight.SemiBold,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
				)
				Text(
					page.description,
					style = YesTheme.typography.bodySmall,
					color = colors.textTertiary,
					maxLines = 2,
					overflow = TextOverflow.Ellipsis,
				)
				Row(
					horizontalArrangement = Arrangement.spacedBy(sizes.spacingTiny),
					verticalAlignment = Alignment.CenterVertically,
				) {
					pages.forEach { item ->
						Box(
							Modifier.size(
								width = if (item == page) 18.dp else 6.dp,
								height = 6.dp,
							).background(
								color = if (item == page) colors.tint else colors.divider,
								shape = YesTheme.shapes.pill,
							),
						)
					}
				}
			}

			Column(
				verticalArrangement = Arrangement.spacedBy(sizes.spacingSmall),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				IconButton(
					onClick = { onPageChange(pages[currentIndex - 1]) },
					enabled = currentIndex > 0,
					size = 34.dp,
					backgroundColor = colors.backgroundVariant,
				) {
					Icon(
						YesIcons.ChevronUp,
						size = sizes.iconSmall,
					)
				}
				IconButton(
					onClick = { onPageChange(pages[currentIndex + 1]) },
					enabled = currentIndex < pages.lastIndex,
					size = 34.dp,
					backgroundColor = colors.backgroundVariant,
				) {
					Icon(
						YesIcons.ChevronDown,
						size = sizes.iconSmall,
					)
				}
			}
		}
	}
}

@Composable
internal fun SectionCard(
	title: String,
	modifier: Modifier = Modifier,
	content: @Composable ColumnScope.() -> Unit,
) = Card(
	modifier = modifier.fillMaxWidth(),
	backgroundColor = YesTheme.colors.background,
) {
	Column(
		Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingLarge),
	) {
		Text(
			title,
			style = YesTheme.typography.label,
			color = YesTheme.colors.textSecondary,
			fontWeight = FontWeight.SemiBold,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
		)
		content()
	}
}

@Composable
internal fun ControlRow(title: String, content: @Composable RowScope.() -> Unit) = Row(
	Modifier.fillMaxWidth(),
	horizontalArrangement = Arrangement.SpaceBetween,
	verticalAlignment = Alignment.CenterVertically,
) {
	Text(
		title,
		Modifier.weight(1f),
		style = YesTheme.typography.body,
		color = YesTheme.colors.textPrimary,
		maxLines = 1,
		overflow = TextOverflow.Ellipsis,
	)
	Row(
		horizontalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingSmall),
		verticalAlignment = Alignment.CenterVertically,
		content = content,
	)
}