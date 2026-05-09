@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.composeunstyled.theme.buildTheme
import work.niggergo.yesui.foundation.utils.RippleStyle
import work.niggergo.yesui.foundation.utils.rememberRippleIndication

@Suppress("unused")
object YesTheme {
	@get:Composable val dark get() = LocalYesDark.current
	@get:Composable val colors get() = LocalYesColors.current
	@get:Composable val typography get() = LocalYesTypography.current
	@get:Composable val shapes get() = LocalYesShapes.current
	@get:Composable val sizes get() = LocalYesSizes.current
	@get:Composable val background get() = LocalYesBackground.current
	@get:Composable val rippleStyle get() = LocalYesRippleStyle.current
}

@Suppress("unused")
@Composable
fun YesTheme(
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
					BrandToken to it.brand,
					BrandVariantToken to it.brandVariant,
					OnBrandToken to it.onBrand,
					DisabledBrandToken to it.disabledBrand,
					TitleColorToken to it.title,
					BackgroundToken to it.background,
					BackgroundVariantToken to it.backgroundVariant,
					SurfaceToken to it.surface,
					ActiveBackgroundToken to it.activeBackground,
					DisabledBackgroundToken to it.disabledBackground,
					DisabledBackgroundVariantToken to it.disabledBackgroundVariant,
					BorderToken to it.border,
					DividerToken to it.divider,
					TextPrimaryToken to it.textPrimary,
					TextSecondaryToken to it.textSecondary,
					TextTertiaryToken to it.textTertiary,
					BackdropToken to it.backdrop,
				)
				defaultContentColor = it.textPrimary
			}

			typography?.let {
				properties[TypographyProperty] = mapOf(
					TitleTextToken to it.title,
					SubtitleTextToken to it.subtitle,
					LabelTextToken to it.label,
					ButtonTextToken to it.button,
					BodyTextToken to it.body,
					BodySmallTextToken to it.bodySmall,
					CaptionTextToken to it.caption,
				)
				defaultTextStyle = it.body
			}

			shapes?.let {
				properties[ShapeProperty] = mapOf(
					SmallShapeToken to it.small,
					MediumShapeToken to it.medium,
					LargeShapeToken to it.large,
					ExtraLargeShapeToken to it.extraLarge,
					PillShapeToken to it.pill,
					ButtonShapeToken to it.button,
					CardShapeToken to it.card,
					DialogShapeToken to it.dialog,
					SheetShapeToken to it.sheet,
					InputShapeToken to it.input,
					IconButtonShapeToken to it.iconButton,
				)
			}

			sizes?.let {
				properties[SizeProperty] = mapOf(
					SpacingTinyToken to it.spacingTiny,
					SpacingSmallToken to it.spacingSmall,
					SpacingMediumToken to it.spacingMedium,
					SpacingLargeToken to it.spacingLarge,
					SpacingExtraLargeToken to it.spacingExtraLarge,
					IconToken to it.icon,
					IconSmallToken to it.iconSmall,
					IconLargeToken to it.iconLarge,
					ControlHeightToken to it.controlHeight,
					TouchTargetToken to it.touchTarget,
				)
			}
		}
	}

	theme {
		CompositionLocalProvider(
			LocalYesDark provides darkMode,
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