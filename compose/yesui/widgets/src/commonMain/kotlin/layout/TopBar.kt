@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.widgets.layout

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.core.theme.YesTheme
import work.niggergo.yesui.core.utils.smoothCapsuleShape
import work.niggergo.yesui.core.widgets.Surface

@Suppress("unused")
@Composable
fun TopBar(
	modifier: Modifier = Modifier,
	containerSpacing: Dp = 12.dp,
	minContainerHeight: Dp = 52.dp,
	shadowElevation: Dp = 16.dp,
	titleShape: Shape = smoothCapsuleShape(),
	actionsShape: Shape = smoothCapsuleShape(),
	titleBackgroundColor: Color = Color.Unspecified,
	actionsBackgroundColor: Color = Color.Unspecified,
	titleContentColor: Color = Color.Unspecified,
	actionsContentColor: Color = Color.Unspecified,
	titleContentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
	actionsContentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
	titleItemSpacing: Dp = 10.dp,
	actionsItemSpacing: Dp = 12.dp,
	titleContainerModifier: Modifier = Modifier,
	actionsContainerModifier: Modifier = Modifier,
	title: @Composable RowScope.() -> Unit,
	actions: (@Composable RowScope.() -> Unit)? = null,
) {
	Row(
		modifier = modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
	) {
		TopBarContainer(
			modifier = titleContainerModifier,
			minHeight = minContainerHeight,
			shadowElevation = shadowElevation,
			shape = titleShape,
			backgroundColor = titleBackgroundColor,
			contentColor = titleContentColor,
			contentPadding = titleContentPadding,
			itemSpacing = titleItemSpacing,
			content = title,
		)

		actions?.let {
			Spacer(Modifier.weight(1f))
			Spacer(Modifier.width(containerSpacing))
			TopBarContainer(
				modifier = actionsContainerModifier,
				minHeight = minContainerHeight,
				shadowElevation = shadowElevation,
				shape = actionsShape,
				backgroundColor = actionsBackgroundColor,
				contentColor = actionsContentColor,
				contentPadding = actionsContentPadding,
				itemSpacing = actionsItemSpacing,
				content = it,
			)
		}
	}
}

@Composable
private fun TopBarContainer(
	modifier: Modifier,
	minHeight: Dp,
	shadowElevation: Dp,
	shape: Shape,
	backgroundColor: Color,
	contentColor: Color,
	contentPadding: PaddingValues,
	itemSpacing: Dp,
	content: @Composable RowScope.() -> Unit,
) {
	val colors = YesTheme.colors

	Surface(
		modifier = modifier.shadow(shadowElevation, shape, clip = false).clip(shape).defaultMinSize(minHeight = minHeight)
			.wrapContentWidth(),
		backgroundColor = backgroundColor.takeIf { it.isSpecified } ?: colors.surface,
		contentColor = contentColor.takeIf { it.isSpecified } ?: colors.content,
		contentPadding = contentPadding,
		contentAlignment = Alignment.Center,
	) {
		Row(
			modifier = Modifier.wrapContentWidth(),
			horizontalArrangement = Arrangement.spacedBy(itemSpacing),
			verticalAlignment = Alignment.CenterVertically,
			content = content,
		)
	}
}
