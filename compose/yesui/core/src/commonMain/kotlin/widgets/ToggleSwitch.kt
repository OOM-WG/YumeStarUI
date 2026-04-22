@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import work.niggergo.yesui.core.theme.YesTheme
import work.niggergo.yesui.core.utils.smoothCapsuleShape
import com.composeunstyled.Thumb as UnstyledThumb
import com.composeunstyled.ToggleSwitch as UnstyledToggleSwitch

@Suppress("unused")
@Composable
fun ToggleSwitch(
	toggled: Boolean,
	modifier: Modifier = Modifier,
	onToggled: ((Boolean) -> Unit)? = null,
	enabled: Boolean = true,
	shape: Shape = smoothCapsuleShape(),
	backgroundColor: Color = if (toggled) YesTheme.colors.tint.copy(alpha = 0.28f) else YesTheme.colors.container,
	contentPadding: PaddingValues = PaddingValues(YesTheme.sizes.spacingTertiary / 2),
	interactionSource: MutableInteractionSource? = null,
	indication: Indication = LocalIndication.current,
	thumb: @Composable () -> Unit = {
		UnstyledThumb(
			shape = smoothCapsuleShape(),
			color = if (toggled) YesTheme.colors.tint else YesTheme.colors.contentSecondary,
		)
	},
) = UnstyledToggleSwitch(
	toggled = toggled,
	modifier = modifier,
	onToggled = onToggled,
	enabled = enabled,
	shape = shape,
	backgroundColor = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.56f),
	contentPadding = contentPadding,
	interactionSource = interactionSource,
	indication = indication,
	thumb = thumb,
)