package work.niggergo.yesui.demo

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.components.common.BackgroundImageCard
import work.niggergo.yesui.foundation.components.Text
import work.niggergo.yesui.foundation.theme.YesTheme

@Composable
internal fun HomePage() {
	val colors = YesTheme.colors

	BackgroundImageCard(
		modifier = Modifier.fillMaxWidth().height(620.dp),
		gradientColor = colors.surface,
	) {
		Column(
			Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.Bottom,
		) {
			Text(
				"Powered by NGA",
				style = YesTheme.typography.subtitle,
				color = colors.textPrimary.copy(alpha = 0.78f),
				fontWeight = FontWeight.Medium,
				modifier = Modifier.align(Alignment.Start),
			)
		}
	}
}