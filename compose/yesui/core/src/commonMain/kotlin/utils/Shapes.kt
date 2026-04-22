@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.utils

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.*
import top.yukonga.miuix.kmp.shapes.*
import work.niggergo.yesui.core.theme.YesTheme

enum class YesShapeSize { Small, Medium, Large }

@Composable
fun smoothShape(size: YesShapeSize) = YesTheme.shapes.run {
	smoothShape(
		when (size) {
			YesShapeSize.Small  -> small
			YesShapeSize.Medium -> medium
			YesShapeSize.Large  -> large
		}
	)
}

@Composable
fun smoothShape(cornerRadius: Dp) = YesTheme.smoothRounding.let { smooth ->
	remember(smooth, cornerRadius) {
		if (smooth) CachedOutlineShape(SmoothRoundedCornerShape(cornerRadius)) else RoundedCornerShape(cornerRadius)
	}
}

@Composable
fun smoothCapsuleShape() = YesTheme.smoothRounding.let { smooth ->
	remember(smooth) {
		if (smooth) CachedOutlineShape(SmoothCapsuleShape()) else CircleShape
	}
}

@Composable
fun smoothUnevenShape(topStart: Dp = 0.dp, topEnd: Dp = 0.dp, bottomEnd: Dp = 0.dp, bottomStart: Dp = 0.dp) =
	YesTheme.smoothRounding.let { smooth ->
		remember(smooth, topStart, topEnd, bottomEnd, bottomStart) {
			if (smooth) CachedOutlineShape(
				SmoothUnevenRoundedCornerShape(topStart, topEnd, bottomEnd, bottomStart),
			) else RoundedCornerShape(topStart, topEnd, bottomEnd, bottomStart)
		}
	}

@Stable
private class CachedOutlineShape(private val delegate: Shape) : Shape {
	private data class CachedOutline(
		val size: Size,
		val layoutDirection: LayoutDirection,
		val density: Float,
		val outline: Outline,
	)

	private var cache = null as CachedOutline?

	override fun createOutline(
		size: Size,
		layoutDirection: LayoutDirection,
		density: Density,
	) = cache?.takeIf { cached ->
		cached.size == size && cached.layoutDirection == layoutDirection && cached.density == density.density
	}?.outline ?: delegate.createOutline(size, layoutDirection, density).also { outline ->
		cache = CachedOutline(
			size = size,
			layoutDirection = layoutDirection,
			density = density.density,
			outline = outline,
		)
	}
}
