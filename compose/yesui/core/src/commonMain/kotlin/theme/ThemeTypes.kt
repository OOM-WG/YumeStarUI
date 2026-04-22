@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import work.niggergo.yesui.core.utils.RippleStyle

private val LightBackgroundBase = Color(0xFFF8F8F8)
private val DarkBackgroundBase = Color(0xFF202020)
private val LightContentBase = Color(0xFF000000)
private val DarkContentBase = Color(0xFFFFFFFF)

@Suppress("unused")
@Immutable
data class YesColors(
	val tint: Color = Color.Unspecified,
	val background: Color = Color.Unspecified,
	val container: Color = Color.Unspecified,
	val surface: Color = Color.Unspecified,
	val content: Color = Color.Unspecified,
	val contentOnTint: Color = Color.Unspecified,
) {
	val isDarkPalette = background.luminance() < 0.5f

	val contentSecondary = content.copy(alpha = 0.72f)
	val contentTertiary = content.copy(alpha = 0.56f)
	val outline = content.copy(alpha = 0.16f)
	val divider = content.copy(alpha = 0.12f)
	val scrim = Color.Black.copy(alpha = 0.56f)
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
	val container = colors.container.takeIf { it.isSpecified } ?: run {
		val containerBase = elevatedContainer(background, darkMode)
		if (tintBackground) blend(tint, containerBase, 0.16f) else containerBase
	}
	val surface = colors.surface.takeIf { it.isSpecified } ?: resolveSurfaceColor(
		background = background,
		tint = tint,
		tintBackground = tintBackground,
	)
	val content = colors.content.takeIf { it.isSpecified } ?: fallbackContent
	val contentOnTint = colors.contentOnTint.takeIf { it.isSpecified } ?: contentColorFor(tint)

	return YesColors(
		tint = tint,
		background = background,
		container = container,
		surface = surface,
		content = content,
		contentOnTint = contentOnTint,
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

	return if (tintBackground) {
		tint.copy(alpha = 0.03f).compositeOver(baseSurface)
	} else {
		baseSurface
	}
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
	val body: TextStyle = TextStyle(
		fontSize = 16.sp,
		lineHeight = 24.sp,
		fontWeight = FontWeight.Normal,
	),
)

@Immutable
data class YesShapes(
	val small: Dp = 8.dp,
	val medium: Dp = 14.dp,
	val large: Dp = 20.dp,
)

@Immutable
data class YesSizes(
	val spacingPrimary: Dp = 16.dp,
	val spacingSecondary: Dp = 12.dp,
	val spacingTertiary: Dp = 8.dp,
	val icon: Dp = 20.dp,
	val iconSmall: Dp = 16.dp,
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
internal val LocalYesSmoothRounding = compositionLocalOf { true }
internal val LocalYesRippleStyle = compositionLocalOf<RippleStyle?> { null }

private fun elevatedContainer(background: Color, darkMode: Boolean) =
	lerp(background, if (darkMode) Color.White else Color.Black, if (darkMode) 0.06f else 0.025f)

private fun blend(foreground: Color, background: Color, ratio: Float) = lerp(background, foreground, ratio.coerceIn(0f, 1f))

private fun contentColorFor(color: Color) = if (color.luminance() > 0.55f) Color(0xFF111111) else Color.White
