@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.composeunstyled.UnstyledTextField
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.TextInput as UnstyledTextInput

typealias TextFieldScope = com.composeunstyled.TextFieldScope

@Suppress("unused")
@Composable
fun TextField(
	state: TextFieldState,
	modifier: Modifier = Modifier,
	editable: Boolean = true,
	cursorBrush: Brush = SolidColor(YesTheme.colors.brand),
	textStyle: TextStyle = YesTheme.typography.body,
	textAlign: TextAlign = TextAlign.Unspecified,
	lineHeight: TextUnit = TextUnit.Unspecified,
	fontSize: TextUnit = TextUnit.Unspecified,
	letterSpacing: TextUnit = TextUnit.Unspecified,
	fontWeight: FontWeight? = null,
	fontFamily: FontFamily? = null,
	singleLine: Boolean = false,
	minLines: Int = 1,
	maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
	onKeyboardAction: KeyboardActionHandler? = null,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource? = null,
	textColor: Color = YesTheme.colors.textPrimary,
	scrollState: ScrollState = rememberScrollState(),
	content: @Composable TextFieldScope.() -> Unit,
) = UnstyledTextField(
	state = state,
	modifier = modifier,
	editable = editable,
	cursorBrush = cursorBrush,
	textStyle = textStyle,
	textAlign = textAlign,
	lineHeight = lineHeight,
	fontSize = fontSize,
	letterSpacing = letterSpacing,
	fontWeight = fontWeight,
	fontFamily = fontFamily,
	singleLine = singleLine,
	minLines = minLines,
	maxLines = maxLines,
	onKeyboardAction = onKeyboardAction,
	keyboardOptions = keyboardOptions,
	visualTransformation = visualTransformation,
	interactionSource = interactionSource,
	textColor = textColor,
	scrollState = scrollState,
	content = content,
)

@Suppress("unused")
@Composable
fun TextFieldScope.TextInput(
	modifier: Modifier = Modifier,
	shape: Shape = YesTheme.shapes.input,
	backgroundColor: Color = YesTheme.colors.backgroundVariant,
	contentPadding: PaddingValues = PaddingValues(
		horizontal = YesTheme.sizes.spacingExtraLarge,
		vertical = YesTheme.sizes.spacingLarge,
	),
	contentColor: Color = YesTheme.colors.textPrimary,
	label: String? = null,
	placeholder: (@Composable () -> Unit)? = null,
	leading: (@Composable () -> Unit)? = null,
	trailing: (@Composable () -> Unit)? = null,
	verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
) = UnstyledTextInput(
	modifier = modifier,
	shape = shape,
	backgroundColor = backgroundColor,
	contentPadding = contentPadding,
	contentColor = contentColor,
	label = label,
	placeholder = placeholder,
	leading = leading,
	trailing = trailing,
	verticalAlignment = verticalAlignment,
)
