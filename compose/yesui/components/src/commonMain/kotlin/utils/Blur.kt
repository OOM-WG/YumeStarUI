@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import top.yukonga.miuix.kmp.blur.*
import work.niggergo.yesui.foundation.theme.YesColors

@Immutable
data class YesBlurStyle(
	val baseColor: Color,
	val tintColor: Color,
	val saturation: Float,
	val contrast: Float,
	val brightness: Float,
)

fun YesColors.navigationBlurStyle() = panelGlass.run {
	YesBlurStyle(
		baseColor = blurBaseColor,
		tintColor = blurTintColor,
		saturation = if (isDarkPalette) 1.06f else 1.02f,
		contrast = if (isDarkPalette) 1.08f else 1.10f,
		brightness = if (isDarkPalette) 0f else -0.05f,
	)
}

fun YesColors.controlBlurStyle() = controlGlass.run {
	YesBlurStyle(
		baseColor = blurBaseColor,
		tintColor = blurTintColor,
		saturation = if (isDarkPalette) 1.08f else 1.03f,
		contrast = if (isDarkPalette) 1.08f else 1.10f,
		brightness = if (isDarkPalette) 0f else -0.03f,
	)
}

fun YesColors.navigationGradientStartColor() = if (isDarkPalette) Color.Black.copy(alpha = 0.36f)
else surface.copy(alpha = 0.23f)

fun YesColors.navigationGradientEndColor() = surface.copy(
	alpha = if (isDarkPalette) 0.18f else 0.16f,
)

fun YesColors.controlGradientStartColor() = controlGlass.fillColor

fun YesColors.controlGradientEndColor() =
	if (isDarkPalette) brand.copy(alpha = 0.06f).compositeOver(controlGlass.blurBaseColor)
	else Color.White.copy(alpha = 0.08f).compositeOver(controlGlass.blurBaseColor)

@Composable
fun YesBlurStyle.blurColors() = BlurDefaults.blurColors(
	blendColors = listOf(
		BlendColorEntry(color = baseColor, mode = BlurBlendMode.SrcOver),
		BlendColorEntry(color = tintColor, mode = BlurBlendMode.SrcOver),
	),
	saturation = saturation,
	contrast = contrast,
	brightness = brightness,
)