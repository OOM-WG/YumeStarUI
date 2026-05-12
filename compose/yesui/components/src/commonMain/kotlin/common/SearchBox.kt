@file:Suppress("PackageDirectoryMismatch", "unused")

package work.niggergo.yesui.components.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.foundation.components.*
import work.niggergo.yesui.foundation.theme.YesTheme
import work.niggergo.yesui.icons.YesBasicIcons

@Composable
fun SearchBox(
	state: TextFieldState,
	modifier: Modifier = Modifier,
	placeholder: String = "...",
	enabled: Boolean = true,
	focusRequester: FocusRequester = remember { FocusRequester() },
	focusShortcut: ((KeyEvent) -> Boolean)? = SearchBoxDefaults.FocusShortcut,
	showShortcut: Boolean = state.text.isEmpty() && focusShortcut != null,
	shortcutText: String = "Ctrl K",
	onSearch: KeyboardActionHandler? = null,
	onClear: (() -> Unit)? = { state.edit { replace(0, length, "") } },
	minHeight: Dp = SearchBoxDefaults.MinHeight,
	borderWidth: Dp = SearchBoxDefaults.BorderWidth,
	contentPadding: PaddingValues = SearchBoxDefaults.ContentPadding,
	colors: SearchBoxColors = SearchBoxDefaults.colors(),
) {
	val interactionSource = remember { MutableInteractionSource() }
	val containerInteractionSource = remember { MutableInteractionSource() }
	val focused by interactionSource.collectIsFocusedAsState()
	val borderColor by animateColorAsState(
		targetValue = when {
			!enabled -> colors.disabledBorderColor
			focused  -> colors.focusedBorderColor
			else     -> colors.borderColor
		},
		label = "SearchBoxBorderColor",
	)
	val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor

	Surface(
		modifier = modifier.searchBoxFocusShortcut(
			enabled = enabled,
			focusRequester = focusRequester,
			focusShortcut = focusShortcut,
		).clickable(
			interactionSource = containerInteractionSource,
			indication = null,
			enabled = enabled,
			onClick = { focusRequester.requestFocus() },
		),
		shape = YesTheme.shapes.input,
		backgroundColor = if (enabled) colors.backgroundColor else colors.disabledBackgroundColor,
		contentColor = contentColor,
		borderColor = borderColor,
		borderWidth = borderWidth,
	) {
		TextField(
			state = state,
			modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
			editable = enabled,
			textStyle = SearchBoxDefaults.textStyle(),
			singleLine = true,
			textColor = contentColor,
			interactionSource = interactionSource,
			keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
			onKeyboardAction = onSearch,
		) {
			TextInput(
				modifier = Modifier.fillMaxWidth().heightIn(min = minHeight),
				shape = YesTheme.shapes.input,
				backgroundColor = Color.Transparent,
				contentColor = contentColor,
				contentPadding = contentPadding,
				leading = {
					Box(Modifier.padding(end = SearchBoxDefaults.ContentGap)) {
						Icon(
							YesBasicIcons.Search,
							size = YesTheme.sizes.iconSmall,
							tint = if (enabled) colors.iconColor else colors.disabledContentColor,
						)
					}
				},
				placeholder = {
					Text(
						placeholder,
						style = SearchBoxDefaults.textStyle(),
						color = if (enabled) colors.placeholderColor else colors.disabledContentColor,
						singleLine = true,
						overflow = TextOverflow.Ellipsis,
					)
				},
				trailing = {
					when {
						state.text.isNotEmpty() && onClear != null -> {
							IconButton(
								onClick = onClear,
								enabled = enabled,
								size = SearchBoxDefaults.ClearButtonSize,
								contentColor = colors.iconColor,
							) {
								Icon(
									YesBasicIcons.Close,
									size = SearchBoxDefaults.ClearIconSize,
									tint = if (enabled) colors.iconColor else colors.disabledContentColor,
								)
							}
						}

						showShortcut                               -> {
							Surface(
								shape = YesTheme.shapes.small,
								backgroundColor = colors.shortcutBackgroundColor,
								contentColor = colors.shortcutContentColor,
								contentPadding = SearchBoxDefaults.ShortcutPadding,
								contentAlignment = Alignment.Center,
							) {
								Text(
									shortcutText,
									style = YesTheme.typography.caption,
									color = if (enabled) colors.shortcutContentColor else colors.disabledContentColor,
									singleLine = true,
								)
							}
						}
					}
				},
			)
		}
	}
}

fun Modifier.searchBoxFocusShortcut(
	enabled: Boolean = true,
	focusRequester: FocusRequester,
	focusShortcut: ((KeyEvent) -> Boolean)? = SearchBoxDefaults.FocusShortcut,
) = onPreviewKeyEvent { event ->
	if (enabled && focusShortcut?.invoke(event) == true) {
		focusRequester.requestFocus()
		true
	} else {
		false
	}
}

object SearchBoxDefaults {
	val MinHeight = 42.dp
	val BorderWidth = 1.dp
	val ContentGap = 8.dp
	val ClearButtonSize = 28.dp
	val ClearIconSize = 16.dp
	val ContentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
	val ShortcutPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
	val FocusShortcut = { event: KeyEvent ->
		event.type == KeyEventType.KeyDown && event.isCtrlPressed && event.key == Key.K
	}

	@Composable
	fun textStyle() = YesTheme.typography.bodySmall

	@Composable
	fun colors(
		backgroundColor: Color = Color.Transparent,
		contentColor: Color = YesTheme.colors.textPrimary,
		placeholderColor: Color = YesTheme.colors.textTertiary,
		iconColor: Color = YesTheme.colors.textTertiary,
		borderColor: Color = YesTheme.colors.divider,
		focusedBorderColor: Color = YesTheme.colors.border,
		shortcutBackgroundColor: Color = YesTheme.colors.activeBackground,
		shortcutContentColor: Color = YesTheme.colors.textTertiary,
		disabledBackgroundColor: Color = YesTheme.colors.disabledBackground,
		disabledContentColor: Color = YesTheme.colors.textTertiary,
		disabledBorderColor: Color = YesTheme.colors.divider,
	) = SearchBoxColors(
		backgroundColor = backgroundColor,
		contentColor = contentColor,
		placeholderColor = placeholderColor,
		iconColor = iconColor,
		borderColor = borderColor,
		focusedBorderColor = focusedBorderColor,
		shortcutBackgroundColor = shortcutBackgroundColor,
		shortcutContentColor = shortcutContentColor,
		disabledBackgroundColor = disabledBackgroundColor,
		disabledContentColor = disabledContentColor,
		disabledBorderColor = disabledBorderColor,
	)
}

data class SearchBoxColors(
	val backgroundColor: Color,
	val contentColor: Color,
	val placeholderColor: Color,
	val iconColor: Color,
	val borderColor: Color,
	val focusedBorderColor: Color,
	val shortcutBackgroundColor: Color,
	val shortcutContentColor: Color,
	val disabledBackgroundColor: Color,
	val disabledContentColor: Color,
	val disabledBorderColor: Color,
)