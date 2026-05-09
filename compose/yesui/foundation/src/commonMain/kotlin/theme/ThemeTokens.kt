@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.composeunstyled.theme.ThemeProperty
import com.composeunstyled.theme.ThemeToken

internal val ColorProperty = ThemeProperty<Color>("yesui.colors")
internal val TypographyProperty = ThemeProperty<TextStyle>("yesui.typography")
internal val ShapeProperty = ThemeProperty<Shape>("yesui.shapes")
internal val SizeProperty = ThemeProperty<Dp>("yesui.sizes")

internal val BrandToken = ThemeToken<Color>("brand")
internal val BrandVariantToken = ThemeToken<Color>("brand_variant")
internal val OnBrandToken = ThemeToken<Color>("on_brand")
internal val DisabledBrandToken = ThemeToken<Color>("disabled_brand")
internal val TitleColorToken = ThemeToken<Color>("title")
internal val BackgroundToken = ThemeToken<Color>("background")
internal val BackgroundVariantToken = ThemeToken<Color>("background_variant")
internal val SurfaceToken = ThemeToken<Color>("surface")
internal val ActiveBackgroundToken = ThemeToken<Color>("active_background")
internal val DisabledBackgroundToken = ThemeToken<Color>("disabled_background")
internal val DisabledBackgroundVariantToken = ThemeToken<Color>("disabled_background_variant")
internal val BorderToken = ThemeToken<Color>("border")
internal val DividerToken = ThemeToken<Color>("divider")
internal val TextPrimaryToken = ThemeToken<Color>("text_primary")
internal val TextSecondaryToken = ThemeToken<Color>("text_secondary")
internal val TextTertiaryToken = ThemeToken<Color>("text_tertiary")
internal val BackdropToken = ThemeToken<Color>("backdrop")

internal val TitleTextToken = ThemeToken<TextStyle>("title")
internal val SubtitleTextToken = ThemeToken<TextStyle>("subtitle")
internal val LabelTextToken = ThemeToken<TextStyle>("label")
internal val ButtonTextToken = ThemeToken<TextStyle>("button")
internal val BodyTextToken = ThemeToken<TextStyle>("body")
internal val BodySmallTextToken = ThemeToken<TextStyle>("body_small")
internal val CaptionTextToken = ThemeToken<TextStyle>("caption")

internal val SmallShapeToken = ThemeToken<Shape>("small")
internal val MediumShapeToken = ThemeToken<Shape>("medium")
internal val LargeShapeToken = ThemeToken<Shape>("large")
internal val ExtraLargeShapeToken = ThemeToken<Shape>("extra_large")
internal val PillShapeToken = ThemeToken<Shape>("pill")
internal val ButtonShapeToken = ThemeToken<Shape>("button")
internal val CardShapeToken = ThemeToken<Shape>("card")
internal val DialogShapeToken = ThemeToken<Shape>("dialog")
internal val SheetShapeToken = ThemeToken<Shape>("sheet")
internal val InputShapeToken = ThemeToken<Shape>("input")
internal val IconButtonShapeToken = ThemeToken<Shape>("icon_button")

internal val SpacingTinyToken = ThemeToken<Dp>("spacing_tiny")
internal val SpacingSmallToken = ThemeToken<Dp>("spacing_small")
internal val SpacingMediumToken = ThemeToken<Dp>("spacing_medium")
internal val SpacingLargeToken = ThemeToken<Dp>("spacing_large")
internal val SpacingExtraLargeToken = ThemeToken<Dp>("spacing_extra_large")

internal val IconToken = ThemeToken<Dp>("icon")
internal val IconSmallToken = ThemeToken<Dp>("icon_small")
internal val IconLargeToken = ThemeToken<Dp>("icon_large")
internal val ControlHeightToken = ThemeToken<Dp>("control_height")
internal val TouchTargetToken = ThemeToken<Dp>("touch_target")