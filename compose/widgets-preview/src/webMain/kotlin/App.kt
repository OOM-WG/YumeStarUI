package work.niggergo.yesui.preview

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import org.jetbrains.compose.resources.*
import work.niggergo.yesui.core.theme.*
import work.niggergo.yesui.core.utils.YesShapeSize
import work.niggergo.yesui.core.utils.smoothShape
import work.niggergo.yesui.core.widgets.*
import work.niggergo.yesui.icons.*
import work.niggergo.yesui.widgets.layout.*

@Immutable
private data class PreviewAction(
	val title: String,
	val caption: String,
	val icon: @Composable () -> Painter,
)

@OptIn(InternalResourceApi::class) private val previewBackgroundResource = DrawableResource(
	id = "bg-img",
	items = setOf(
		ResourceItem(
			qualifiers = emptySet(),
			path = "composeResources/work.niggergo.yesui.preview.generated.resources/drawable/bg2.jpg",
			offset = -1,
			size = -1,
		),
	),
)

@Composable
fun App() {
	Scaffold(
		modifier = Modifier.fillMaxSize(),
		pageContentPadding = PaddingValues(
			start = 12.dp,
			top = 72.dp,
			end = 12.dp,
			bottom = 12.dp,
		),
		navigationContent = { info ->
			SidebarContent(info)
		},
	) { contentPadding ->
		val sizes = YesTheme.sizes

		Box(
			modifier = Modifier.fillMaxSize().padding(contentPadding),
		) {
			PreviewTopBar(
				modifier = Modifier.fillMaxWidth().align(Alignment.TopStart).offset(y = (-56).dp),
			)

			Column(
				modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
				verticalArrangement = Arrangement.spacedBy(sizes.spacingPrimary),
			) {
				Spacer(Modifier.height(10.dp))
				HeroWallpaperCard()
			}
		}
	}
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
	YesTheme(
		background = YesBackground.PainterBackground(
			painter = painterResource(previewBackgroundResource),
			alpha = 0.94f,
		),
		colors = YesColors(
			tint = Color(0xFFD81C3F),
		),
		tintBackground = false,
		content = content,
	)
}

@Composable
private fun SidebarContent(info: ScaffoldNavigationLayoutInfo) {
	NavigationLayout(
		info = info,
		modifier = Modifier.fillMaxWidth().fillMaxHeight(),
		header = { layoutInfo ->
			Sidebar(
				info = layoutInfo,
				modifier = Modifier.align(Alignment.TopCenter),
			)
		},
		footer = { layoutInfo ->
			SidebarActions(
				info = layoutInfo,
			)
		},
	)
}

@Composable
private fun Sidebar(
	info: ScaffoldNavigationLayoutInfo,
	modifier: Modifier = Modifier,
) {
	val colors = YesTheme.colors
	val expanded = info.value == ScaffoldNavigationMode.Expanded

	if (expanded) {
		Column(
			modifier = modifier,
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(4.dp),
		) {
			Text(
				text = "YumeStarUI",
				style = YesTheme.typography.title,
				fontWeight = FontWeight.SemiBold,
				color = colors.content,
				singleLine = true,
			)
		}
	} else {
		Text(
			text = "YesUI",
			modifier = modifier,
			style = YesTheme.typography.subtitle,
			fontWeight = FontWeight.SemiBold,
			color = colors.content,
			singleLine = true,
		)
	}
}

@Composable
private fun SidebarActions(
	info: ScaffoldNavigationLayoutInfo,
) {
	val expanded = info.value == ScaffoldNavigationMode.Expanded
	val actions = listOf(
		PreviewAction("Home", "Home", { YesIcons.House }),
		PreviewAction("YumeStarUI", "YumeStarUI", { YesIcons.Info }),
	)
	Column(
		verticalArrangement = Arrangement.spacedBy(if (expanded) 18.dp else 24.dp),
		horizontalAlignment = if (expanded) Alignment.Start else Alignment.CenterHorizontally,
	) {
		actions.forEach { action ->
			SidebarActionItem(
				action = action,
				expanded = expanded,
			)
		}
	}
}

@Composable
private fun PreviewTopBar(modifier: Modifier = Modifier) {
	val colors = YesTheme.colors

	TopBar(
		modifier = modifier.padding(horizontal = 6.dp),
		title = {
			Icon(
				painter = YesIcons.Sparkles,
				contentDescription = null,
				tint = colors.tint,
			)
			Text(
				text = "YumeStarUI",
				style = YesTheme.typography.subtitle,
				color = colors.content,
				fontWeight = FontWeight.SemiBold,
				singleLine = true,
			)
		},
		actions = {
			Icon(
				painter = YesIcons.ExternalLink,
				contentDescription = null,
				tint = colors.content,
			)
		},
	)
}

@Composable
private fun SidebarActionItem(
	action: PreviewAction,
	expanded: Boolean,
) {
	val colors = YesTheme.colors

	Row(
		modifier = if (expanded) {
			Modifier.widthIn(min = 152.dp)
		} else {
			Modifier.wrapContentWidth().padding(horizontal = 8.dp, vertical = 4.dp)
		},
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(12.dp),
	) {
		Box(
			modifier = Modifier.size(22.dp),
			contentAlignment = Alignment.Center,
		) {
			Icon(
				painter = action.icon(),
				contentDescription = action.title,
				tint = colors.content.copy(alpha = 0.88f),
			)
		}

		if (expanded) {
			Column(
				modifier = Modifier.weight(1f),
				verticalArrangement = Arrangement.spacedBy(2.dp),
			) {
				Text(
					text = action.title,
					style = YesTheme.typography.subtitle,
					color = colors.content,
					singleLine = true,
					overflow = TextOverflow.Ellipsis,
				)
				Text(
					text = action.caption,
					style = YesTheme.typography.label,
					color = colors.contentSecondary,
					singleLine = true,
				)
			}
		}
	}
}

@Composable
private fun HeroWallpaperCard() {
	val colors = YesTheme.colors
	val pageSurface = colors.surface

	Card(
		modifier = Modifier.fillMaxWidth().height(620.dp),
		shape = smoothShape(YesShapeSize.Large),
		backgroundColor = Color.Transparent,
		contentPadding = PaddingValues(0.dp),
	) {
		Box(modifier = Modifier.fillMaxSize()) {
			Background(modifier = Modifier.fillMaxSize())

			Box(
				modifier = Modifier.fillMaxSize().background(
					Brush.verticalGradient(
						colorStops = arrayOf(
							0f to Color.Transparent,
							0.58f to Color.Transparent,
							0.82f to pageSurface.copy(alpha = 0.84f),
							1f to pageSurface,
						),
					),
				),
			)

			Column(
				modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 28.dp),
				verticalArrangement = Arrangement.Bottom,
			) {
				Text(
					text = "Powered by NGA",
					style = YesTheme.typography.subtitle,
					color = colors.content.copy(alpha = 0.78f),
					fontWeight = FontWeight.Medium,
				)
			}
		}
	}
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	ComposeViewport { AppTheme { App() } }
}
