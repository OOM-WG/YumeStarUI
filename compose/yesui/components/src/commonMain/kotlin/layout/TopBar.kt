@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.components.layout

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.foundation.components.Surface
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun TopBar(
	modifier: Modifier = Modifier,
	containerSpacing: Dp = 8.dp,
	minContainerHeight: Dp = 44.dp,
	shadowElevation: Dp = 16.dp,
	titleShape: Shape = YesTheme.shapes.pill,
	actionsShape: Shape = YesTheme.shapes.pill,
	titleBackgroundColor: Color = Color.Unspecified,
	actionsBackgroundColor: Color = Color.Unspecified,
	titleContentColor: Color = Color.Unspecified,
	actionsContentColor: Color = Color.Unspecified,
	titleContentPadding: PaddingValues = PaddingValues(horizontal = 14.dp, vertical = 3.dp),
	actionsContentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
	titleItemSpacing: Dp = 8.dp,
	actionsItemSpacing: Dp = 0.dp,
	titleContainerModifier: Modifier = Modifier,
	actionsContainerModifier: Modifier = Modifier,
	title: @Composable RowScope.() -> Unit,
	actions: (@Composable RowScope.() -> Unit)? = null,
) {
	val colors = YesTheme.colors

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
			defaultBackgroundColor = colors.surface,
			defaultContentColor = colors.title,
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
				defaultBackgroundColor = colors.surface,
				defaultContentColor = colors.textPrimary,
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
	defaultBackgroundColor: Color,
	defaultContentColor: Color,
	contentPadding: PaddingValues,
	itemSpacing: Dp,
	content: @Composable RowScope.() -> Unit,
) {
	Surface(
		modifier = modifier.shadow(shadowElevation, shape, clip = false).defaultMinSize(minHeight = minHeight)
			.wrapContentWidth(),
		shape = shape,
		backgroundColor = backgroundColor.takeIf { it.isSpecified } ?: defaultBackgroundColor,
		contentColor = contentColor.takeIf { it.isSpecified } ?: defaultContentColor,
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