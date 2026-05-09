@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.composeunstyled.UnstyledThumb
import com.composeunstyled.UnstyledToggleSwitch
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun ToggleSwitch(
	toggled: Boolean,
	modifier: Modifier = Modifier,
	onToggled: ((Boolean) -> Unit)? = null,
	enabled: Boolean = true,
	shape: Shape = YesTheme.shapes.pill,
	backgroundColor: Color = if (toggled) YesTheme.colors.brand else YesTheme.colors.divider,
	contentPadding: PaddingValues = PaddingValues(YesTheme.sizes.spacingTiny),
	interactionSource: MutableInteractionSource? = null,
	indication: Indication = LocalIndication.current,
	thumb: @Composable () -> Unit = {
		UnstyledThumb(
			shape = YesTheme.shapes.pill,
			color = if (enabled) YesTheme.colors.onBrand else YesTheme.colors.disabledBackground,
		)
	},
) = UnstyledToggleSwitch(
	toggled = toggled,
	modifier = modifier,
	onToggled = onToggled,
	enabled = enabled,
	shape = shape,
	backgroundColor = if (enabled) backgroundColor else if (toggled) YesTheme.colors.disabledBrand else YesTheme.colors.disabledBackgroundVariant,
	contentPadding = contentPadding,
	interactionSource = interactionSource,
	indication = indication,
	thumb = thumb,
)
