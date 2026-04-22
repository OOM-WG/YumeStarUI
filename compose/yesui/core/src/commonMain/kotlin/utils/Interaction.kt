@file:Suppress("PackageDirectoryMismatch", "unused")

package work.niggergo.yesui.core.utils

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.createRippleModifierNode
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.*
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import work.niggergo.yesui.core.theme.LocalYesRippleStyle
import work.niggergo.yesui.core.theme.YesTheme

@Immutable
data class RippleStyle(
	val bounded: Boolean,
	val radius: Dp,
	val color: Color,
	val rippleAlpha: RippleAlpha,
) {
	companion object {
		@Composable
		fun default(
			bounded: Boolean = true,
			radius: Dp = Dp.Unspecified,
			color: Color = Color.Unspecified,
			rippleAlpha: RippleAlpha? = null,
		): RippleStyle {
			val colors = YesTheme.colors
			val lightTheme = colors.background.luminance() >= 0.5f
			val color = when {
				color.isSpecified -> color
				else              -> lerp(
					start = colors.content,
					stop = colors.container,
					fraction = if (lightTheme) 0.18f else 0.26f,
				)
			}
			val alpha = rippleAlpha ?: defaultRippleAlpha(
				contentColor = color,
				lightTheme = lightTheme,
			)

			return remember(bounded, radius, color, alpha) {
				RippleStyle(
					bounded = bounded,
					radius = radius,
					color = color,
					rippleAlpha = alpha,
				)
			}
		}

		private fun defaultRippleAlpha(
			contentColor: Color,
			lightTheme: Boolean,
		) = when {
			lightTheme && contentColor.luminance() > 0.5f -> RippleAlpha(
				pressedAlpha = 0.24f,
				focusedAlpha = 0.24f,
				draggedAlpha = 0.16f,
				hoveredAlpha = 0.08f,
			)

			lightTheme                                    -> RippleAlpha(
				pressedAlpha = 0.12f,
				focusedAlpha = 0.12f,
				draggedAlpha = 0.08f,
				hoveredAlpha = 0.04f,
			)

			else                                          -> RippleAlpha(
				pressedAlpha = 0.10f,
				focusedAlpha = 0.12f,
				draggedAlpha = 0.08f,
				hoveredAlpha = 0.04f,
			)
		}
	}
}

@Composable
fun rememberRippleIndication(style: RippleStyle? = null): Indication {
	val style = style ?: LocalYesRippleStyle.current ?: RippleStyle.default()
	val colorState = rememberUpdatedState(style.color)
	val rippleAlphaState = rememberUpdatedState(style.rippleAlpha)

	return remember(style.bounded, style.radius) {
		YesRippleIndication(
			bounded = style.bounded,
			radius = style.radius,
			color = colorState,
			rippleAlpha = rippleAlphaState,
		)
	}
}

fun Modifier.rippleClickable(
	rippleStyle: RippleStyle? = null,
	interactionSource: MutableInteractionSource? = null,
	enabled: Boolean = true,
	onClickLabel: String? = null,
	role: Role? = null,
	onClick: () -> Unit,
) = composed {
	clickable(
		interactionSource = interactionSource,
		indication = rememberRippleIndication(rippleStyle),
		enabled = enabled,
		onClickLabel = onClickLabel,
		role = role,
		onClick = onClick,
	)
}

fun Modifier.rippleCombinedClickable(
	rippleStyle: RippleStyle? = null,
	interactionSource: MutableInteractionSource? = null,
	enabled: Boolean = true,
	onClickLabel: String? = null,
	role: Role? = null,
	onLongClickLabel: String? = null,
	onLongClick: (() -> Unit)? = null,
	onDoubleClick: (() -> Unit)? = null,
	onClick: () -> Unit,
) = composed {
	combinedClickable(
		interactionSource = interactionSource,
		indication = rememberRippleIndication(rippleStyle),
		enabled = enabled,
		onClickLabel = onClickLabel,
		role = role,
		onLongClickLabel = onLongClickLabel,
		onLongClick = onLongClick,
		onDoubleClick = onDoubleClick,
		onClick = onClick,
	)
}

fun Modifier.rippleToggleable(
	value: Boolean,
	rippleStyle: RippleStyle? = null,
	interactionSource: MutableInteractionSource? = null,
	enabled: Boolean = true,
	role: Role? = null,
	onValueChange: (Boolean) -> Unit,
) = composed {
	toggleable(
		value = value,
		interactionSource = interactionSource,
		indication = rememberRippleIndication(rippleStyle),
		enabled = enabled,
		role = role,
		onValueChange = onValueChange,
	)
}

fun Modifier.rippleSelectable(
	selected: Boolean,
	rippleStyle: RippleStyle? = null,
	interactionSource: MutableInteractionSource? = null,
	enabled: Boolean = true,
	role: Role? = null,
	onClick: () -> Unit,
) = composed {
	selectable(
		selected = selected,
		interactionSource = interactionSource,
		indication = rememberRippleIndication(rippleStyle),
		enabled = enabled,
		role = role,
		onClick = onClick,
	)
}

@Stable
private class YesRippleIndication(
	private val bounded: Boolean,
	private val radius: Dp,
	private val color: State<Color>,
	private val rippleAlpha: State<RippleAlpha>,
) : IndicationNodeFactory {
	override fun create(interactionSource: InteractionSource): DelegatableNode = createRippleModifierNode(
		interactionSource = interactionSource,
		bounded = bounded,
		radius = radius,
		color = { color.value },
		rippleAlpha = { rippleAlpha.value },
	)

	override fun equals(other: Any?) = when {
		this === other                -> true
		other !is YesRippleIndication -> false
		else                          -> bounded == other.bounded && radius == other.radius
	}

	override fun hashCode() = 31 * bounded.hashCode() + radius.hashCode()
}
