@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.core.theme.YesTheme

@Suppress("unused")
@Composable
fun Divider(
	modifier: Modifier = Modifier,
	color: Color = Color.Unspecified,
	thickness: Dp = 1.dp,
	vertical: Boolean = false,
) {
	val color = color.takeIf { it.isSpecified } ?: YesTheme.colors.divider
	val modifier = if (vertical) Modifier.fillMaxHeight().then(modifier)
	else Modifier.fillMaxWidth().then(modifier)

	Box(
		modifier.then(
			if (vertical) Modifier.background(color).width(thickness)
			else Modifier.background(color).height(thickness)
		)
	)
}