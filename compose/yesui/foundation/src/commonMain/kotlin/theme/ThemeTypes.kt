@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import work.niggergo.yesui.foundation.utils.RippleStyle

private val LightBackgroundBase = Color(0xFFF8F8F8)
private val DarkBackgroundBase = Color(0xFF202020)
private val LightContentBase = Color(0xFF000000)
private val DarkContentBase = Color(0xFFFFFFFF)

@Suppress("unused")
@Immutable
data class YesColors(
	val tint: Color = Color.Unspecified,
	val tintVariant: Color = Color.Unspecified,
	val onTint: Color = Color.Unspecified,
	val disabledTint: Color = Color.Unspecified,
	val title: Color = Color.Unspecified,
	val background: Color = Color.Unspecified,
	val backgroundVariant: Color = Color.Unspecified,
	val surface: Color = Color.Unspecified,
	val activeBackground: Color = Color.Unspecified,
	val disabledBackground: Color = Color.Unspecified,
	val disabledBackgroundVariant: Color = Color.Unspecified,
	val border: Color = Color.Unspecified,
	val divider: Color = Color.Unspecified,
	val textPrimary: Color = Color.Unspecified,
	val textSecondary: Color = Color.Unspecified,
	val textTertiary: Color = Color.Unspecified,
	val backdrop: Color = Color.Unspecified,
) {
	val isDarkPalette = background.isSpecified && background.luminance() < 0.5f
	val panelGlass = resolvePanelGlassColors(surface, isDarkPalette)
	val controlGlass = resolveControlGlassColors(surface, tint, isDarkPalette)
}

@Immutable
data class YesGlassColors(
	val blurBaseColor: Color,
	val blurTintColor: Color,
) {
	val fillColor = blurTintColor.compositeOver(blurBaseColor)
}

internal fun resolveColors(
	colors: YesColors,
	darkMode: Boolean,
	tintBackground: Boolean,
	systemColors: SystemColors,
): YesColors {
	val tint = colors.tint.takeIf { it.isSpecified } ?: systemColors.accentColor ?: JavaFXDefaultAccentColor
	val fallbackBackground = systemColors.backgroundColor ?: if (darkMode) DarkBackgroundBase else LightBackgroundBase
	val fallbackContent = systemColors.foregroundColor ?: if (darkMode) DarkContentBase else LightContentBase
	val background = colors.background.takeIf { it.isSpecified } ?: if (tintBackground) blend(
		tint, fallbackBackground, 0.09f
	) else fallbackBackground
	val backgroundVariant = colors.backgroundVariant.takeIf { it.isSpecified } ?: run {
		val backgroundVariantBase = elevatedContainer(background, darkMode)
		if (tintBackground) blend(tint, backgroundVariantBase, 0.16f) else backgroundVariantBase
	}
	val surface = colors.surface.takeIf { it.isSpecified } ?: resolveSurfaceColor(
		background = background,
		tint = tint,
		tintBackground = tintBackground,
	)
	val textPrimary = colors.textPrimary.takeIf { it.isSpecified } ?: fallbackContent
	val textSecondary = colors.textSecondary.takeIf { it.isSpecified } ?: textPrimary.copy(alpha = 0.72f)
	val textTertiary = colors.textTertiary.takeIf { it.isSpecified } ?: textPrimary.copy(alpha = 0.56f)
	val onTint = colors.onTint.takeIf { it.isSpecified } ?: contentColorFor(tint)
	val tintVariant =
		colors.tintVariant.takeIf { it.isSpecified } ?: blend(tint, backgroundVariant, if (darkMode) 0.30f else 0.18f)
	val disabledTint =
		colors.disabledTint.takeIf { it.isSpecified } ?: blend(tint, backgroundVariant, if (darkMode) 0.24f else 0.28f)
	val title = colors.title.takeIf { it.isSpecified } ?: tint
	val activeBackground = colors.activeBackground.takeIf { it.isSpecified } ?: blend(
		textPrimary, backgroundVariant, if (darkMode) 0.10f else 0.05f
	)
	val disabledBackground = colors.disabledBackground.takeIf { it.isSpecified } ?: backgroundVariant
	val disabledBackgroundVariant = colors.disabledBackgroundVariant.takeIf { it.isSpecified } ?: blend(
		textPrimary, backgroundVariant, if (darkMode) 0.12f else 0.08f
	)
	val border = colors.border.takeIf { it.isSpecified } ?: tint
	val divider = colors.divider.takeIf { it.isSpecified } ?: disabledBackgroundVariant
	val backdrop = colors.backdrop.takeIf { it.isSpecified } ?: Color.Black.copy(alpha = 0.56f)

	return YesColors(
		tint = tint,
		tintVariant = tintVariant,
		onTint = onTint,
		disabledTint = disabledTint,
		title = title,
		background = background,
		backgroundVariant = backgroundVariant,
		surface = surface,
		activeBackground = activeBackground,
		disabledBackground = disabledBackground,
		disabledBackgroundVariant = disabledBackgroundVariant,
		border = border,
		divider = divider,
		textPrimary = textPrimary,
		textSecondary = textSecondary,
		textTertiary = textTertiary,
		backdrop = backdrop,
	)
}

private fun resolveSurfaceColor(
	background: Color,
	tint: Color,
	tintBackground: Boolean,
): Color {
	val darkPalette = background.luminance() < 0.5f
	val surfaceTint = if (darkPalette) Color.Black else Color.White
	val surfaceTintAlpha = if (darkPalette) 0.18f else 0.74f
	val baseSurface = surfaceTint.copy(alpha = surfaceTintAlpha).compositeOver(background)

	return if (tintBackground) tint.copy(alpha = 0.03f).compositeOver(baseSurface)
	else baseSurface
}

private fun resolvePanelGlassColors(
	surface: Color,
	darkPalette: Boolean,
) = YesGlassColors(
	blurBaseColor = if (darkPalette) Color.Black.copy(alpha = 0.24f) else surface.copy(alpha = 0.13f),
	blurTintColor = Color.Black.copy(alpha = 0.10f),
)

private fun resolveControlGlassColors(
	surface: Color,
	tint: Color,
	darkPalette: Boolean,
) = YesGlassColors(
	blurBaseColor = if (darkPalette) Color.Black.copy(alpha = 0.22f) else surface.copy(alpha = 0.16f),
	blurTintColor = if (darkPalette) tint.copy(alpha = 0.05f) else Color.White.copy(alpha = 0.12f),
)

@Immutable
data class YesTypography(
	val title: TextStyle = TextStyle(
		fontSize = 24.sp,
		lineHeight = 30.sp,
		fontWeight = FontWeight.SemiBold,
	),
	val subtitle: TextStyle = TextStyle(
		fontSize = 18.sp,
		lineHeight = 24.sp,
		fontWeight = FontWeight.Medium,
	),
	val label: TextStyle = TextStyle(
		fontSize = 14.sp,
		lineHeight = 20.sp,
		fontWeight = FontWeight.Medium,
	),
	val button: TextStyle = TextStyle(
		fontSize = 16.sp,
		lineHeight = 20.sp,
		fontWeight = FontWeight.Medium,
	),
	val body: TextStyle = TextStyle(
		fontSize = 16.sp,
		lineHeight = 24.sp,
		fontWeight = FontWeight.Normal,
	),
	val bodySmall: TextStyle = TextStyle(
		fontSize = 14.sp,
		lineHeight = 20.sp,
		fontWeight = FontWeight.Normal,
	),
	val caption: TextStyle = TextStyle(
		fontSize = 12.sp,
		lineHeight = 16.sp,
		fontWeight = FontWeight.Normal,
	),
)

@Immutable
data class YesShapes(
	val small: Shape = RoundedCornerShape(6.dp),
	val medium: Shape = RoundedCornerShape(12.dp),
	val large: Shape = RoundedCornerShape(16.dp),
	val extraLarge: Shape = RoundedCornerShape(24.dp),
	val pill: Shape = CircleShape,
	val button: Shape = large,
	val card: Shape = large,
	val dialog: Shape = extraLarge,
	val sheet: Shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
	val input: Shape = medium,
	val iconButton: Shape = pill,
)

@Immutable
data class YesSizes(
	val spacingTiny: Dp = 4.dp,
	val spacingSmall: Dp = 6.dp,
	val spacingMedium: Dp = 8.dp,
	val spacingLarge: Dp = 12.dp,
	val spacingExtraLarge: Dp = 16.dp,
	val icon: Dp = 20.dp,
	val iconSmall: Dp = 16.dp,
	val iconLarge: Dp = 24.dp,
	val controlHeight: Dp = 40.dp,
	val touchTarget: Dp = 48.dp,
)

@Suppress("unused")
@Immutable
sealed interface YesBackground {
	@Immutable
	data class SolidColor(val color: Color) : YesBackground

	@Immutable
	data class GradientBrush(val brush: Brush) : YesBackground

	@Immutable
	data class PainterBackground(
		val painter: Painter,
		val contentScale: ContentScale = ContentScale.Crop,
		val alignment: Alignment = Alignment.Center,
		val alpha: Float = 1f,
	) : YesBackground
}

internal val LocalYesDark = compositionLocalOf { false }
internal val LocalYesBackground = compositionLocalOf<YesBackground?> { null }
internal val LocalYesColors = compositionLocalOf {
	resolveColors(
		colors = YesColors(),
		darkMode = false,
		tintBackground = true,
		systemColors = SystemColors(),
	)
}
internal val LocalYesTypography = compositionLocalOf { YesTypography() }
internal val LocalYesShapes = compositionLocalOf { YesShapes() }
internal val LocalYesSizes = compositionLocalOf { YesSizes() }
internal val LocalYesRippleStyle = compositionLocalOf<RippleStyle?> { null }

private fun elevatedContainer(background: Color, darkMode: Boolean) =
	lerp(background, if (darkMode) Color.White else Color.Black, if (darkMode) 0.06f else 0.025f)

private fun blend(foreground: Color, background: Color, ratio: Float) = lerp(background, foreground, ratio.coerceIn(0f, 1f))

private fun contentColorFor(color: Color) = if (color.luminance() > 0.55f) Color(0xFF111111) else Color.White