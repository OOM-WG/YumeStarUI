@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.patterns.feedback

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.foundation.components.*
import work.niggergo.yesui.foundation.theme.YesTheme
import work.niggergo.yesui.icons.*

enum class AnnotationBoxVariant { Info, Warning, Note, Tip, Check, Danger, Custom, }

@Suppress("unused")
@Composable
fun AnnotationBox(
	text: String,
	modifier: Modifier = Modifier,
	variant: AnnotationBoxVariant = AnnotationBoxVariant.Custom,
	icon: Painter? = AnnotationBoxDefaults.iconFor(variant),
	color: Color = AnnotationBoxDefaults.colorFor(variant),
	colors: AnnotationBoxColors = AnnotationBoxDefaults.colors(color),
	border: Boolean = true,
	contentPadding: PaddingValues = AnnotationBoxDefaults.contentPadding(),
) = Surface(
	modifier = modifier,
	shape = YesTheme.shapes.medium,
	backgroundColor = colors.backgroundColor,
	contentColor = colors.contentColor,
	borderColor = colors.borderColor,
	borderWidth = if (border) AnnotationBoxDefaults.BorderWidth else 0.dp,
) {
	Row(
		Modifier.fillMaxWidth().padding(contentPadding),
		horizontalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingLarge),
		verticalAlignment = Alignment.Top,
	) {
		icon?.let {
			Icon(
				painter = it,
				size = AnnotationBoxDefaults.IconSize,
				tint = colors.iconColor,
				modifier = Modifier.padding(top = 1.dp),
			)
		}
		Text(
			text,
			Modifier.weight(1f),
			style = YesTheme.typography.bodySmall,
			color = colors.contentColor,
			overflow = TextOverflow.Ellipsis,
		)
	}
}

@Suppress("unused")
@Composable
fun InfoBox(text: String, modifier: Modifier = Modifier) =
	AnnotationBox(text = text, modifier = modifier, variant = AnnotationBoxVariant.Info)

@Suppress("unused")
@Composable
fun WarningBox(text: String, modifier: Modifier = Modifier) =
	AnnotationBox(text = text, modifier = modifier, variant = AnnotationBoxVariant.Warning)

@Suppress("unused")
@Composable
fun NoteBox(text: String, modifier: Modifier = Modifier) =
	AnnotationBox(text = text, modifier = modifier, variant = AnnotationBoxVariant.Note)

@Suppress("unused")
@Composable
fun TipBox(text: String, modifier: Modifier = Modifier) =
	AnnotationBox(text = text, modifier = modifier, variant = AnnotationBoxVariant.Tip)

@Suppress("unused")
@Composable
fun CheckAnnotationBox(text: String, modifier: Modifier = Modifier) =
	AnnotationBox(text = text, modifier = modifier, variant = AnnotationBoxVariant.Check)

@Suppress("unused")
@Composable
fun DangerBox(text: String, modifier: Modifier = Modifier) =
	AnnotationBox(text = text, modifier = modifier, variant = AnnotationBoxVariant.Danger)

object AnnotationBoxDefaults {
	val BorderWidth = 1.dp
	val IconSize = 18.dp

	@Composable
	fun contentPadding() = PaddingValues(
		horizontal = YesTheme.sizes.spacingExtraLarge,
		vertical = YesTheme.sizes.spacingLarge + YesTheme.sizes.spacingTiny / 2,
	)

	@Composable
	fun iconFor(variant: AnnotationBoxVariant): Painter? = when (variant) {
		AnnotationBoxVariant.Info    -> YesIcons.Info
		AnnotationBoxVariant.Warning -> YesIcons.TriangleAlert
		AnnotationBoxVariant.Note    -> YesIcons.Notebook
		AnnotationBoxVariant.Tip     -> YesIcons.Lightbulb
		AnnotationBoxVariant.Check   -> YesIcons.CircleCheck
		AnnotationBoxVariant.Danger  -> YesIcons.TriangleAlert
		AnnotationBoxVariant.Custom  -> null
	}

	@Composable
	fun colorFor(variant: AnnotationBoxVariant): Color = when (variant) {
		AnnotationBoxVariant.Info    -> Color.Unspecified
		AnnotationBoxVariant.Warning -> Color.Yellow
		AnnotationBoxVariant.Note    -> Color.Blue
		AnnotationBoxVariant.Tip     -> Color.Green
		AnnotationBoxVariant.Check   -> Color.Green
		AnnotationBoxVariant.Danger  -> Color.Red
		AnnotationBoxVariant.Custom  -> Color.Unspecified
	}

	@Composable
	fun colors(color: Color = Color.Unspecified): AnnotationBoxColors {
		val colors = YesTheme.colors
		val resolvedColor = (color.takeIf { it.isSpecified } ?: colors.tint).opaqueOver(colors.surface)
		val bgColor = opaque(
			blend(
				foreground = resolvedColor,
				background = colors.surface,
				ratio = if (colors.isDarkPalette) 0.18f else 0.10f,
			)
		)
		val contentColor = opaque(
			blend(
				foreground = resolvedColor,
				background = colors.textPrimary,
				ratio = if (colors.isDarkPalette) 0.52f else 0.68f,
			)
		)
		val iconColor = opaque(
			blend(
				foreground = resolvedColor,
				background = colors.textSecondary,
				ratio = if (colors.isDarkPalette) 0.58f else 0.72f,
			)
		)
		val borderColor = opaque(
			blend(
				foreground = resolvedColor,
				background = colors.divider,
				ratio = if (colors.isDarkPalette) 0.38f else 0.28f,
			)
		)

		return remember(bgColor, contentColor, iconColor, borderColor) {
			AnnotationBoxColors(
				backgroundColor = bgColor,
				contentColor = contentColor,
				iconColor = iconColor,
				borderColor = borderColor,
			)
		}
	}
}

@Immutable
data class AnnotationBoxColors(
	val backgroundColor: Color,
	val contentColor: Color,
	val iconColor: Color,
	val borderColor: Color,
)

private fun blend(
	foreground: Color,
	background: Color,
	ratio: Float,
) = lerp(background, foreground, ratio.coerceIn(0f, 1f))

private fun opaque(color: Color) = color.copy(alpha = 1f)
private fun Color.opaqueOver(background: Color) = if (alpha >= 1f) this else compositeOver(background).copy(alpha = 1f)