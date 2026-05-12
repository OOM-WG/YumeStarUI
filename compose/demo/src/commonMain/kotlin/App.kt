package work.niggergo.yesui.demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import work.niggergo.yesui.components.layout.Scaffold
import work.niggergo.yesui.foundation.components.PortalHost
import work.niggergo.yesui.foundation.theme.*

@Composable
fun App() {
	var page by remember { mutableStateOf(DemoPage.Home) }

	PortalHost(modifier = Modifier.fillMaxSize()) {
		Scaffold(
			modifier = Modifier.fillMaxSize(),
			pageContentPadding = PaddingValues(
				start = 12.dp,
				top = 72.dp,
				end = 12.dp,
				bottom = 12.dp,
			),
			navigationContent = { info ->
				DemoNavigation(
					info = info,
					page = page,
					onPageClick = { page = it },
				)
			},
			topBar = {
				DemoTopBar(
					page = page,
					modifier = Modifier.fillMaxWidth().align(Alignment.TopStart).padding(horizontal = 6.dp),
				)
			},
		) { contentPadding ->
			val sizes = YesTheme.sizes
			val layoutDirection = LocalLayoutDirection.current

			Box(Modifier.fillMaxSize()) {
				Column(
					Modifier.fillMaxSize().padding(
						start = contentPadding.calculateStartPadding(layoutDirection),
						end = contentPadding.calculateEndPadding(layoutDirection),
					).verticalScroll(rememberScrollState()),
					verticalArrangement = Arrangement.spacedBy(sizes.spacingExtraLarge),
				) {
					Spacer(Modifier.height(contentPadding.calculateTopPadding() + 10.dp))
					when (page) {
						DemoPage.Home -> HomePage()
						else          -> ComponentDemoPage(
							page = page,
							onPageChange = { page = it },
						)
					}
					Spacer(Modifier.height(contentPadding.calculateBottomPadding()))
				}
			}
		}
	}
}

@Composable
fun AppTheme(content: @Composable () -> Unit) = YesTheme(
	background = YesBackground.PainterBackground(
		painter = painterResource(demoBackgroundResource),
		alpha = 0.94f,
	),
	colors = YesColors(
		tint = Color(0xFFD81C3F),
	),
	tintBackground = false,
	content = content,
)