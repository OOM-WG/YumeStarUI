@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified
import com.composeunstyled.LocalContentColor
import com.composeunstyled.UnstyledIcon
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun Icon(
	painter: Painter,
	contentDescription: String? = null,
	modifier: Modifier = Modifier,
	size: Dp = Dp.Unspecified,
	tint: Color = LocalContentColor.current.takeOrElse { YesTheme.colors.textPrimary },
) = UnstyledIcon(
	painter = painter,
	contentDescription = contentDescription,
	modifier = Modifier.size(if (size.isSpecified) size else YesTheme.sizes.icon).then(modifier),
	tint = tint,
)

@Suppress("unused")
@Composable
fun Icon(
	imageBitmap: ImageBitmap,
	contentDescription: String? = null,
	modifier: Modifier = Modifier,
	size: Dp = Dp.Unspecified,
	tint: Color = LocalContentColor.current.takeOrElse { YesTheme.colors.textPrimary },
) = UnstyledIcon(
	imageBitmap = imageBitmap,
	contentDescription = contentDescription,
	modifier = Modifier.size(if (size.isSpecified) size else YesTheme.sizes.icon).then(modifier),
	tint = tint,
)

@Suppress("unused")
@Composable
fun Icon(
	imageVector: ImageVector,
	contentDescription: String? = null,
	modifier: Modifier = Modifier,
	size: Dp = Dp.Unspecified,
	tint: Color = LocalContentColor.current.takeOrElse { YesTheme.colors.textPrimary },
) = UnstyledIcon(
	imageVector = imageVector,
	contentDescription = contentDescription,
	modifier = Modifier.size(if (size.isSpecified) size else YesTheme.sizes.icon).then(modifier),
	tint = tint,
)