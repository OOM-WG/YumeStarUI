package work.niggergo.yesui.demo

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.components.layout.*
import work.niggergo.yesui.foundation.components.*
import work.niggergo.yesui.foundation.theme.YesTheme
import work.niggergo.yesui.icons.*
import work.niggergo.yesui.patterns.navigation.NavigationFooterAction
import work.niggergo.yesui.patterns.navigation.NavigationFooterActions

private const val ProjectUrl = "https://github.com/OOM-WG/YumeStarUI"

@Composable
internal fun DemoNavigation(
	info: ScaffoldNavigationLayoutInfo,
	page: DemoPage,
	onPageClick: (DemoPage) -> Unit,
) = NavigationLayout(
	info = info,
	modifier = Modifier.fillMaxWidth().fillMaxHeight(),
	header = { layoutInfo ->
		DemoSidebar(
			info = layoutInfo,
			modifier = Modifier.align(Alignment.TopCenter),
		)
	},
	footer = { layoutInfo ->
		DemoSidebarActions(
			info = layoutInfo,
			page = page,
			onPageClick = onPageClick,
		)
	},
)

@Composable
internal fun DemoTopBar(page: DemoPage, modifier: Modifier = Modifier) {
	val colors = YesTheme.colors
	val uriHandler = LocalUriHandler.current

	TopBar(
		modifier = modifier.padding(horizontal = 6.dp),
		title = {
			if (page == DemoPage.Home) Icon(
				YesIcons.Sparkles,
				tint = colors.tint,
			)
			Text(
				if (page == DemoPage.Home) "YumeStarUI" else page.title,
				style = YesTheme.typography.subtitle,
				color = colors.textPrimary,
				fontWeight = FontWeight.SemiBold,
				singleLine = true,
				overflow = TextOverflow.Ellipsis,
			)
		},
		actions = if (page == DemoPage.Home) ({
			IconButton(
				onClick = { uriHandler.openUri(ProjectUrl) },
				size = 34.dp,
				contentColor = colors.textPrimary,
			) {
				Icon(
					YesIcons.ExternalLink,
					contentDescription = "Open GitHub",
					size = YesTheme.sizes.iconSmall,
				)
			}
		})
		else null,
	)
}

@Composable
private fun DemoSidebar(info: ScaffoldNavigationLayoutInfo, modifier: Modifier = Modifier) {
	val colors = YesTheme.colors
	val expanded = info.value == ScaffoldNavigationMode.Expanded

	if (expanded) Column(
		modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(4.dp),
	) {
		Text(
			"YumeStarUI",
			style = YesTheme.typography.title,
			fontWeight = FontWeight.SemiBold,
			color = colors.textPrimary,
			singleLine = true,
		)
	}
	else Text(
		"YesUI",
		modifier,
		style = YesTheme.typography.subtitle,
		fontWeight = FontWeight.SemiBold,
		color = colors.textPrimary,
		singleLine = true,
	)
}

@Composable
private fun DemoSidebarActions(
	info: ScaffoldNavigationLayoutInfo, page: DemoPage, onPageClick: (DemoPage) -> Unit
) = NavigationFooterActions(
	info = info,
	actions = DemoPage.entries.map { item ->
		NavigationFooterAction(
			painter = item.icon(),
			title = item.title,
			selected = page == item,
			description = item.description,
			onClick = { onPageClick(item) },
		)
	},
)