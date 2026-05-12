@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.patterns.preference

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.state.ToggleableState
import work.niggergo.yesui.components.common.CardItem
import work.niggergo.yesui.foundation.components.*

@Suppress("unused")
@Composable
fun PreferenceCard(
	title: String,
	modifier: Modifier = Modifier,
	description: String? = null,
	icon: (@Composable () -> Unit)? = null,
	enabled: Boolean = true,
	onClick: (() -> Unit)? = null,
	endContent: (@Composable RowScope.() -> Unit)? = null,
) = CardItem(
	title = title,
	modifier = modifier,
	description = description,
	icon = icon,
	enabled = enabled,
	onClick = onClick,
	endContent = endContent,
)

@Suppress("unused")
@Composable
fun PreferenceCard(
	title: String,
	painter: Painter,
	modifier: Modifier = Modifier,
	description: String? = null,
	enabled: Boolean = true,
	onClick: (() -> Unit)? = null,
	endContent: (@Composable RowScope.() -> Unit)? = null,
) = PreferenceCard(
	title = title,
	modifier = modifier,
	description = description,
	icon = { Icon(painter) },
	enabled = enabled,
	onClick = onClick,
	endContent = endContent,
)

@Suppress("unused")
@Composable
fun PreferenceCard(
	title: String,
	imageBitmap: ImageBitmap,
	modifier: Modifier = Modifier,
	description: String? = null,
	enabled: Boolean = true,
	onClick: (() -> Unit)? = null,
	endContent: (@Composable RowScope.() -> Unit)? = null,
) = PreferenceCard(
	title = title,
	modifier = modifier,
	description = description,
	icon = { Icon(imageBitmap) },
	enabled = enabled,
	onClick = onClick,
	endContent = endContent,
)

@Suppress("unused")
@Composable
fun PreferenceCard(
	title: String,
	imageVector: ImageVector,
	modifier: Modifier = Modifier,
	description: String? = null,
	enabled: Boolean = true,
	onClick: (() -> Unit)? = null,
	endContent: (@Composable RowScope.() -> Unit)? = null,
) = PreferenceCard(
	title = title,
	modifier = modifier,
	description = description,
	icon = { Icon(imageVector) },
	enabled = enabled,
	onClick = onClick,
	endContent = endContent,
)

@Suppress("unused")
@Composable
fun SwitchPreferenceCard(
	title: String,
	checked: Boolean,
	onCheckedChange: ((Boolean) -> Unit)?,
	modifier: Modifier = Modifier,
	description: String? = null,
	icon: (@Composable () -> Unit)? = null,
	enabled: Boolean = true,
) = PreferenceCard(
	title = title,
	modifier = modifier,
	description = description,
	icon = icon,
	enabled = enabled,
	onClick = if (enabled && onCheckedChange != null) ({ onCheckedChange(!checked) }) else null,
	endContent = {
		ToggleSwitch(
			toggled = checked,
			onToggled = onCheckedChange,
			enabled = enabled && onCheckedChange != null,
		)
	},
)

@Suppress("unused")
@Composable
fun CheckboxPreferenceCard(
	title: String,
	value: ToggleableState,
	onClick: (() -> Unit)?,
	modifier: Modifier = Modifier,
	description: String? = null,
	icon: (@Composable () -> Unit)? = null,
	enabled: Boolean = true,
) = PreferenceCard(
	title = title,
	modifier = modifier,
	description = description,
	icon = icon,
	enabled = enabled,
	onClick = if (enabled) onClick else null,
	endContent = {
		TriStateCheckBox(
			value = value,
			onClick = onClick ?: {},
			enabled = enabled && onClick != null,
		)
	},
)