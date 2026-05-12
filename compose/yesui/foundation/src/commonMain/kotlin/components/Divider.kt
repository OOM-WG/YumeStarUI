@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledHorizontalSeparator
import com.composeunstyled.UnstyledVerticalSeparator
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun Divider(
	modifier: Modifier = Modifier,
	color: Color = Color.Unspecified,
	thickness: Dp = 1.dp,
	vertical: Boolean = false,
) {
	val color = color.takeIf { it.isSpecified } ?: YesTheme.colors.divider

	if (vertical) UnstyledVerticalSeparator(
		color = color,
		modifier = Modifier.fillMaxHeight().then(modifier),
		thickness = thickness,
	)
	else UnstyledHorizontalSeparator(
		color = color,
		modifier = Modifier.fillMaxWidth().then(modifier),
		thickness = thickness,
	)
}