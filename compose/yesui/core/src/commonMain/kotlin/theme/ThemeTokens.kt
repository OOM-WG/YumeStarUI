@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.composeunstyled.theme.ThemeProperty
import com.composeunstyled.theme.ThemeToken

internal val ColorProperty = ThemeProperty<Color>("yesui.colors")
internal val TypographyProperty = ThemeProperty<TextStyle>("yesui.typography")
internal val ShapeProperty = ThemeProperty<Dp>("yesui.shapes")
internal val SizeProperty = ThemeProperty<Dp>("yesui.sizes")

internal val TintToken = ThemeToken<Color>("tint")
internal val BackgroundToken = ThemeToken<Color>("background")
internal val ContainerToken = ThemeToken<Color>("container")
internal val ContentToken = ThemeToken<Color>("content")
internal val ContentOnTintToken = ThemeToken<Color>("content_on_tint")

internal val TitleToken = ThemeToken<TextStyle>("title")
internal val SubtitleToken = ThemeToken<TextStyle>("subtitle")
internal val LabelToken = ThemeToken<TextStyle>("label")
internal val BodyToken = ThemeToken<TextStyle>("body")

internal val SmallShapeToken = ThemeToken<Dp>("small")
internal val MediumShapeToken = ThemeToken<Dp>("medium")
internal val LargeShapeToken = ThemeToken<Dp>("large")

internal val SpacingPrimaryToken = ThemeToken<Dp>("spacing_primary")
internal val SpacingSecondaryToken = ThemeToken<Dp>("spacing_secondary")
internal val SpacingTertiaryToken = ThemeToken<Dp>("spacing_tertiary")

internal val IconToken = ThemeToken<Dp>("icon")
internal val IconSmallToken = ThemeToken<Dp>("icon_small")