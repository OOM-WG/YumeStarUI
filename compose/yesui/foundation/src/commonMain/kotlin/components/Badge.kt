@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun Badge(
	text: String,
	modifier: Modifier = Modifier,
	icon: (@Composable () -> Unit)? = null,
	enabled: Boolean = true,
	color: Color = Color.Unspecified,
	colors: BadgeColors = BadgeDefaults.colors(color),
	shape: Shape = YesTheme.shapes.pill,
	border: Boolean = false,
	minWidth: Dp = if (icon == null) BadgeDefaults.MinWidth else BadgeDefaults.MinWidthWithIcon,
	height: Dp = BadgeDefaults.Height,
	contentPadding: PaddingValues = BadgeDefaults.contentPadding(),
	iconSpacing: Dp = YesTheme.sizes.spacingTiny,
) {
	val bgColor = if (enabled) colors.backgroundColor else colors.disabledBackgroundColor
	val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor
	val iconColor = if (enabled) colors.iconColor else colors.disabledIconColor
	val borderColor = if (enabled) colors.borderColor else colors.disabledBorderColor

	Surface(
		modifier = modifier,
		shape = shape,
		backgroundColor = bgColor,
		contentColor = contentColor,
		borderColor = borderColor,
		borderWidth = if (border) BadgeDefaults.BorderWidth else 0.dp,
	) {
		Row(
			Modifier.widthIn(min = minWidth).height(height).padding(contentPadding),
			horizontalArrangement = Arrangement.spacedBy(iconSpacing, Alignment.CenterHorizontally),
			verticalAlignment = Alignment.CenterVertically,
		) {
			icon?.let {
				Surface(
					contentColor = iconColor,
					contentAlignment = Alignment.Center,
				) { it() }
			}
			Text(
				text,
				style = YesTheme.typography.caption,
				color = contentColor,
				fontWeight = FontWeight.Bold,
				singleLine = true,
				overflow = TextOverflow.Ellipsis,
			)
		}
	}
}

@Suppress("unused")
@Composable
fun Badge(
	text: String,
	painter: Painter,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	color: Color = Color.Unspecified,
	colors: BadgeColors = BadgeDefaults.colors(color),
	shape: Shape = YesTheme.shapes.pill,
	border: Boolean = false,
	minWidth: Dp = BadgeDefaults.MinWidthWithIcon,
	height: Dp = BadgeDefaults.Height,
	contentPadding: PaddingValues = BadgeDefaults.contentPadding(),
	iconSpacing: Dp = YesTheme.sizes.spacingTiny,
) = Badge(
	text = text,
	modifier = modifier,
	icon = { Icon(painter, size = BadgeDefaults.IconSize) },
	enabled = enabled,
	color = color,
	colors = colors,
	shape = shape,
	border = border,
	minWidth = minWidth,
	height = height,
	contentPadding = contentPadding,
	iconSpacing = iconSpacing,
)

@Suppress("unused")
@Composable
fun Badge(
	text: String,
	imageBitmap: ImageBitmap,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	color: Color = Color.Unspecified,
	colors: BadgeColors = BadgeDefaults.colors(color),
	shape: Shape = YesTheme.shapes.pill,
	border: Boolean = false,
	minWidth: Dp = BadgeDefaults.MinWidthWithIcon,
	height: Dp = BadgeDefaults.Height,
	contentPadding: PaddingValues = BadgeDefaults.contentPadding(),
	iconSpacing: Dp = YesTheme.sizes.spacingTiny,
) = Badge(
	text = text,
	modifier = modifier,
	icon = { Icon(imageBitmap = imageBitmap, contentDescription = null, size = BadgeDefaults.IconSize) },
	enabled = enabled,
	color = color,
	colors = colors,
	shape = shape,
	border = border,
	minWidth = minWidth,
	height = height,
	contentPadding = contentPadding,
	iconSpacing = iconSpacing,
)

@Suppress("unused")
@Composable
fun Badge(
	text: String,
	imageVector: ImageVector,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	color: Color = Color.Unspecified,
	colors: BadgeColors = BadgeDefaults.colors(color),
	shape: Shape = YesTheme.shapes.pill,
	border: Boolean = false,
	minWidth: Dp = BadgeDefaults.MinWidthWithIcon,
	height: Dp = BadgeDefaults.Height,
	contentPadding: PaddingValues = BadgeDefaults.contentPadding(),
	iconSpacing: Dp = YesTheme.sizes.spacingTiny,
) = Badge(
	text = text,
	modifier = modifier,
	icon = { Icon(imageVector, size = BadgeDefaults.IconSize) },
	enabled = enabled,
	color = color,
	colors = colors,
	shape = shape,
	border = border,
	minWidth = minWidth,
	height = height,
	contentPadding = contentPadding,
	iconSpacing = iconSpacing,
)

object BadgeDefaults {
	val BorderWidth = 1.dp
	val MinWidth = 44.dp
	val MinWidthWithIcon = 64.dp
	val Height = 24.dp
	val IconSize = 14.dp

	@Composable
	fun contentPadding() = PaddingValues(horizontal = YesTheme.sizes.spacingMedium, vertical = 0.dp)

	@Composable
	fun colors(
		color: Color = Color.Unspecified,
		disabledBackgroundColor: Color = YesTheme.colors.disabledBackground,
		disabledContentColor: Color = YesTheme.colors.textTertiary,
		disabledIconColor: Color = disabledContentColor,
		disabledBorderColor: Color = YesTheme.colors.divider,
	): BadgeColors {
		val colors = YesTheme.colors
		val resolvedColor = (color.takeIf { it.isSpecified } ?: colors.tint).opaqueOver(colors.surface)
		val darkPalette = colors.isDarkPalette
		val bgColor = opaque(
			blend(
				foreground = resolvedColor,
				background = colors.surface,
				ratio = if (darkPalette) 0.22f else 0.12f,
			)
		)
		val contentColor = opaque(
			blend(
				foreground = resolvedColor,
				background = colors.textPrimary,
				ratio = if (darkPalette) 0.64f else 0.78f,
			)
		)
		val iconColor = opaque(
			blend(
				foreground = resolvedColor,
				background = colors.textSecondary,
				ratio = if (darkPalette) 0.62f else 0.74f,
			)
		)
		val borderColor = opaque(
			blend(
				foreground = resolvedColor,
				background = colors.divider,
				ratio = if (darkPalette) 0.40f else 0.32f,
			)
		)

		return remember(
			bgColor,
			contentColor,
			iconColor,
			borderColor,
			disabledBackgroundColor,
			disabledContentColor,
			disabledIconColor,
			disabledBorderColor,
		) {
			BadgeColors(
				backgroundColor = bgColor,
				contentColor = contentColor,
				iconColor = iconColor,
				borderColor = borderColor,
				disabledBackgroundColor = disabledBackgroundColor,
				disabledContentColor = disabledContentColor,
				disabledIconColor = disabledIconColor,
				disabledBorderColor = disabledBorderColor,
			)
		}
	}
}

@Immutable
data class BadgeColors(
	val backgroundColor: Color,
	val contentColor: Color,
	val iconColor: Color,
	val borderColor: Color,
	val disabledBackgroundColor: Color,
	val disabledContentColor: Color,
	val disabledIconColor: Color,
	val disabledBorderColor: Color,
)

private fun blend(
	foreground: Color, background: Color, ratio: Float
) = lerp(background, foreground, ratio.coerceIn(0f, 1f))

private fun opaque(color: Color) = color.copy(alpha = 1f)
private fun Color.opaqueOver(background: Color) = if (alpha >= 1f) this else compositeOver(background).copy(alpha = 1f)