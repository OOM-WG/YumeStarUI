@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.patterns.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.components.layout.ScaffoldNavigationLayoutInfo
import work.niggergo.yesui.components.layout.ScaffoldNavigationMode
import work.niggergo.yesui.foundation.components.*
import work.niggergo.yesui.foundation.theme.YesTheme
import work.niggergo.yesui.foundation.utils.rippleClickable

@Suppress("unused")
@Immutable
class NavigationFooterAction(
	val icon: @Composable () -> Unit,
	val title: String,
	val enabled: Boolean,
	val selected: Boolean = false,
	val description: String?,
	val disabledDescription: String?,
	val onClick: () -> Unit,
) {
	constructor(
		painter: Painter,
		title: String,
		enabled: Boolean = true,
		selected: Boolean = false,
		description: String? = null,
		disabledDescription: String? = null,
		onClick: () -> Unit,
	) : this(
		icon = { Icon(painter, title) },
		title = title,
		enabled = enabled,
		selected = selected,
		description = description,
		disabledDescription = disabledDescription,
		onClick = onClick,
	)

	constructor(
		imageBitmap: ImageBitmap,
		title: String,
		enabled: Boolean = true,
		selected: Boolean = false,
		description: String? = null,
		disabledDescription: String? = null,
		onClick: () -> Unit,
	) : this(
		icon = { Icon(imageBitmap, title) },
		title = title,
		enabled = enabled,
		selected = selected,
		description = description,
		disabledDescription = disabledDescription,
		onClick = onClick,
	)

	constructor(
		imageVector: ImageVector,
		title: String,
		enabled: Boolean = true,
		selected: Boolean = false,
		description: String? = null,
		disabledDescription: String? = null,
		onClick: () -> Unit,
	) : this(
		icon = { Icon(imageVector, title) },
		title = title,
		enabled = enabled,
		selected = selected,
		description = description,
		disabledDescription = disabledDescription,
		onClick = onClick,
	)
}

@Suppress("unused")
@Composable
fun NavigationFooterActions(
	info: ScaffoldNavigationLayoutInfo,
	actions: List<NavigationFooterAction>,
	modifier: Modifier = Modifier,
	itemMinWidth: Dp = 168.dp,
	iconContainerSize: Dp = 22.dp,
	itemSpacing: Dp = if (info.value == ScaffoldNavigationMode.Expanded) 18.dp else 24.dp,
	contentSpacing: Dp = 12.dp,
	descriptionSpacing: Dp = 2.dp,
	expandedContentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
	collapsedContentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
) {
	val expanded = info.value == ScaffoldNavigationMode.Expanded

	Column(
		modifier,
		Arrangement.spacedBy(itemSpacing),
		if (expanded) Alignment.Start else Alignment.CenterHorizontally,
	) {
		actions.forEach { action ->
			NavigationFooterActionContent(
				action = action,
				expanded = expanded,
				itemMinWidth = itemMinWidth,
				iconContainerSize = iconContainerSize,
				contentSpacing = contentSpacing,
				descriptionSpacing = descriptionSpacing,
				contentPadding = if (expanded) expandedContentPadding else collapsedContentPadding,
			)
		}
	}
}

@Composable
private fun NavigationFooterActionContent(
	action: NavigationFooterAction,
	expanded: Boolean,
	itemMinWidth: Dp,
	iconContainerSize: Dp,
	contentSpacing: Dp,
	descriptionSpacing: Dp,
	contentPadding: PaddingValues,
) {
	val colors = YesTheme.colors
	val shape = YesTheme.shapes.button
	val contentColor = when {
		!action.enabled -> colors.textTertiary
		action.selected -> colors.tint
		else            -> colors.textPrimary.copy(alpha = 0.88f)
	}
	val desc = if (action.enabled) action.description else action.disabledDescription ?: action.description

	Surface(
		modifier = Modifier.then(if (expanded) Modifier.widthIn(min = itemMinWidth) else Modifier.wrapContentWidth())
			.clip(shape).rippleClickable(
				enabled = action.enabled,
				role = Role.Button,
				onClick = action.onClick,
			),
		shape = shape,
		contentColor = contentColor,
		contentPadding = contentPadding,
		contentAlignment = Alignment.CenterStart,
	) {
		Row(
			if (expanded) Modifier.widthIn(min = itemMinWidth) else Modifier.wrapContentWidth(),
			horizontalArrangement = Arrangement.spacedBy(contentSpacing),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Box(
				Modifier.size(iconContainerSize),
				Alignment.Center,
			) { action.icon() }

			if (expanded) Column(
				Modifier.weight(1f),
				verticalArrangement = Arrangement.spacedBy(descriptionSpacing),
			) {
				Text(
					action.title,
					style = YesTheme.typography.subtitle,
					color = contentColor,
					singleLine = true,
					overflow = TextOverflow.Ellipsis,
				)
				desc?.let {
					Text(
						it,
						style = YesTheme.typography.label,
						color = when {
							!action.enabled -> colors.textTertiary
							else            -> colors.textSecondary
						},
						maxLines = 2,
						overflow = TextOverflow.Ellipsis,
					)
				}
			}
		}
	}
}