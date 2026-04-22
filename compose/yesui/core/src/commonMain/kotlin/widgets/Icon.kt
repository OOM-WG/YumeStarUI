@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified
import work.niggergo.yesui.core.theme.YesTheme
import com.composeunstyled.Icon as UnstyledIcon

@Suppress("unused")
@Composable
fun Icon(
	painter: Painter,
	contentDescription: String? = null,
	modifier: Modifier = Modifier,
	size: Dp = Dp.Unspecified,
	tint: Color = Color.Unspecified,
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
	tint: Color = Color.Unspecified,
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
	tint: Color = Color.Unspecified,
) = UnstyledIcon(
	imageVector = imageVector,
	contentDescription = contentDescription,
	modifier = Modifier.size(if (size.isSpecified) size else YesTheme.sizes.icon).then(modifier),
	tint = tint,
)