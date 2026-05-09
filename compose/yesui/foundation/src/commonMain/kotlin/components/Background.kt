@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import work.niggergo.yesui.foundation.theme.YesBackground
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun Background(
	modifier: Modifier = Modifier, background: YesBackground? = null
) = (background ?: YesTheme.background)?.let { bg ->
	when (bg) {
		is YesBackground.SolidColor        -> Box(Modifier.fillMaxSize().background(bg.color).then(modifier))
		is YesBackground.GradientBrush     -> Box(Modifier.fillMaxSize().background(bg.brush).then(modifier))
		is YesBackground.PainterBackground -> Image(
			bg.painter, null, Modifier.fillMaxSize().then(modifier),
			bg.alignment, bg.contentScale,
			bg.alpha,
		)
	}
} ?: Unit