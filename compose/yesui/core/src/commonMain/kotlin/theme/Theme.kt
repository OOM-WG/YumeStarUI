@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.composeunstyled.theme.buildTheme
import work.niggergo.yesui.core.utils.RippleStyle
import work.niggergo.yesui.core.utils.rememberRippleIndication

@Suppress("unused")
object YesTheme {
	@get:Composable val colors get() = LocalYesColors.current
	@get:Composable val typography get() = LocalYesTypography.current
	@get:Composable val shapes get() = LocalYesShapes.current
	@get:Composable val sizes get() = LocalYesSizes.current
	@get:Composable val smoothRounding get() = LocalYesSmoothRounding.current
	@get:Composable val background get() = LocalYesBackground.current
	@get:Composable val rippleStyle get() = LocalYesRippleStyle.current
}

@Suppress("unused")
@Composable
fun YesTheme(
	smoothCorners: Boolean = true,
	tintBackground: Boolean = true,
	darkMode: Boolean = isSystemInDarkTheme(),
	background: YesBackground? = null,
	colors: YesColors? = YesColors(),
	typography: YesTypography? = YesTypography(),
	shapes: YesShapes? = YesShapes(),
	sizes: YesSizes? = YesSizes(),
	rippleStyle: RippleStyle? = null,
	content: @Composable () -> Unit,
) {
	val colors = colors?.let { resolveColors(it, darkMode, tintBackground, getSystemColors()) }

	val theme = remember(colors, typography, shapes, sizes) {
		buildTheme {
			name = "YesTheme"

			colors?.let {
				properties[ColorProperty] = mapOf(
					TintToken to it.tint,
					BackgroundToken to it.background,
					ContainerToken to it.container,
					ContentToken to it.content,
					ContentOnTintToken to it.contentOnTint,
				)
				defaultContentColor = it.content
			}

			typography?.let {
				properties[TypographyProperty] = mapOf(
					TitleToken to it.title,
					SubtitleToken to it.subtitle,
					LabelToken to it.label,
					BodyToken to it.body,
				)
				defaultTextStyle = it.body
			}

			shapes?.let {
				properties[ShapeProperty] = mapOf(
					SmallShapeToken to it.small,
					MediumShapeToken to it.medium,
					LargeShapeToken to it.large,
				)
			}

			sizes?.let {
				properties[SizeProperty] = mapOf(
					SpacingPrimaryToken to it.spacingPrimary,
					SpacingSecondaryToken to it.spacingSecondary,
					SpacingTertiaryToken to it.spacingTertiary,
					IconToken to it.icon,
					IconSmallToken to it.iconSmall,
				)
			}
		}
	}

	theme {
		CompositionLocalProvider(
			LocalYesSmoothRounding provides smoothCorners,
			LocalYesBackground provides background,
			LocalYesRippleStyle provides rippleStyle,
		) {
			@Suppress("SpreadOperator") CompositionLocalProvider(
				*listOfNotNull(
					colors?.let { LocalYesColors provides it },
					typography?.let { LocalYesTypography provides it },
					shapes?.let { LocalYesShapes provides it },
					sizes?.let { LocalYesSizes provides it },
				).toTypedArray()
			) {
				CompositionLocalProvider(
					LocalIndication provides rememberRippleIndication(),
					content = content,
				)
			}
		}
	}
}