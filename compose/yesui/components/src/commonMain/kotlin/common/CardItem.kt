@file:Suppress("PackageDirectoryMismatch", "unused")

package work.niggergo.yesui.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.foundation.components.*
import work.niggergo.yesui.foundation.theme.YesTheme
import work.niggergo.yesui.foundation.utils.rippleClickable

@Composable
fun CardItem(
	title: String,
	modifier: Modifier = Modifier,
	description: String? = null,
	icon: (@Composable () -> Unit)? = null,
	enabled: Boolean = true,
	onClick: (() -> Unit)? = null,
	minHeight: Dp = CardItemDefaults.minHeight(description),
	contentPadding: PaddingValues = CardItemDefaults.contentPadding(),
	contentSpacing: Dp = YesTheme.sizes.spacingLarge,
	titleDescriptionSpacing: Dp = YesTheme.sizes.spacingTiny,
	endContent: (@Composable RowScope.() -> Unit)? = null,
) {
	val colors = YesTheme.colors
	val shape = YesTheme.shapes.card
	val contentColor = if (enabled) colors.textPrimary else colors.textTertiary
	val descriptionColor = if (enabled) colors.textTertiary else colors.textTertiary.copy(alpha = 0.72f)
	val clickableModifier = if (onClick != null) Modifier.clip(shape).rippleClickable(
		enabled = enabled,
		role = Role.Button,
		onClick = onClick,
	)
	else Modifier

	Card(
		modifier = modifier.then(clickableModifier),
		shape = shape,
		contentPadding = PaddingValues(0.dp),
	) {
		Row(
			Modifier.fillMaxWidth().heightIn(min = minHeight).padding(contentPadding),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(contentSpacing),
		) {
			icon?.let {
				Box(
					Modifier.size(YesTheme.sizes.iconLarge),
					Alignment.Center,
				) { it() }
			}

			Column(
				Modifier.weight(1f),
				verticalArrangement = Arrangement.spacedBy(titleDescriptionSpacing, Alignment.CenterVertically),
			) {
				Text(
					title,
					style = YesTheme.typography.body,
					color = contentColor,
					fontWeight = FontWeight.Medium,
					singleLine = true,
					overflow = TextOverflow.Ellipsis,
				)
				description?.let {
					Text(
						it,
						style = YesTheme.typography.caption,
						color = descriptionColor,
						singleLine = true,
						overflow = TextOverflow.Ellipsis,
					)
				}
			}

			endContent?.let {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingSmall),
					content = it,
				)
			}
		}
	}
}

@Composable
fun CardItem(
	title: String,
	painter: Painter,
	modifier: Modifier = Modifier,
	description: String? = null,
	enabled: Boolean = true,
	onClick: (() -> Unit)? = null,
	minHeight: Dp = CardItemDefaults.minHeight(description),
	contentPadding: PaddingValues = CardItemDefaults.contentPadding(),
	contentSpacing: Dp = YesTheme.sizes.spacingLarge,
	titleDescriptionSpacing: Dp = YesTheme.sizes.spacingTiny,
	endContent: (@Composable RowScope.() -> Unit)? = null,
) = CardItem(
	title = title,
	modifier = modifier,
	description = description,
	icon = { Icon(painter) },
	enabled = enabled,
	onClick = onClick,
	minHeight = minHeight,
	contentPadding = contentPadding,
	contentSpacing = contentSpacing,
	titleDescriptionSpacing = titleDescriptionSpacing,
	endContent = endContent,
)

@Composable
fun CardItem(
	title: String,
	imageBitmap: ImageBitmap,
	modifier: Modifier = Modifier,
	description: String? = null,
	enabled: Boolean = true,
	onClick: (() -> Unit)? = null,
	minHeight: Dp = CardItemDefaults.minHeight(description),
	contentPadding: PaddingValues = CardItemDefaults.contentPadding(),
	contentSpacing: Dp = YesTheme.sizes.spacingLarge,
	titleDescriptionSpacing: Dp = YesTheme.sizes.spacingTiny,
	endContent: (@Composable RowScope.() -> Unit)? = null,
) = CardItem(
	title = title,
	modifier = modifier,
	description = description,
	icon = { Icon(imageBitmap) },
	enabled = enabled,
	onClick = onClick,
	minHeight = minHeight,
	contentPadding = contentPadding,
	contentSpacing = contentSpacing,
	titleDescriptionSpacing = titleDescriptionSpacing,
	endContent = endContent,
)

@Composable
fun CardItem(
	title: String,
	imageVector: ImageVector,
	modifier: Modifier = Modifier,
	description: String? = null,
	enabled: Boolean = true,
	onClick: (() -> Unit)? = null,
	minHeight: Dp = CardItemDefaults.minHeight(description),
	contentPadding: PaddingValues = CardItemDefaults.contentPadding(),
	contentSpacing: Dp = YesTheme.sizes.spacingLarge,
	titleDescriptionSpacing: Dp = YesTheme.sizes.spacingTiny,
	endContent: (@Composable RowScope.() -> Unit)? = null,
) = CardItem(
	title = title,
	modifier = modifier,
	description = description,
	icon = { Icon(imageVector) },
	enabled = enabled,
	onClick = onClick,
	minHeight = minHeight,
	contentPadding = contentPadding,
	contentSpacing = contentSpacing,
	titleDescriptionSpacing = titleDescriptionSpacing,
	endContent = endContent,
)

object CardItemDefaults {
	val MinHeight = 50.dp
	val MinHeightWithDescription = 64.dp

	fun minHeight(description: String?) = if (description == null) MinHeight else MinHeightWithDescription

	@Composable
	fun contentPadding() = PaddingValues(horizontal = YesTheme.sizes.spacingExtraLarge, vertical = 0.dp)
}