@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledTextField
import work.niggergo.yesui.foundation.theme.YesTheme
import com.composeunstyled.TextFieldScope as UnstyledTextFieldScope
import com.composeunstyled.TextInput as UnstyledTextInput

@Stable
class TextFieldScope internal constructor(internal val delegate: UnstyledTextFieldScope)

@Suppress("unused")
@Composable
fun TextField(
	state: TextFieldState,
	modifier: Modifier = Modifier,
	editable: Boolean = true,
	readOnly: Boolean = false,
	cursorBrush: Brush = SolidColor(YesTheme.colors.tint),
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
	inputTransformation: InputTransformation? = null,
	outputTransformation: OutputTransformation? = null,
	onKeyboardAction: KeyboardActionHandler? = null,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource? = null,
	textColor: Color = YesTheme.colors.textPrimary,
	scrollState: ScrollState = rememberScrollState(),
	content: @Composable TextFieldScope.() -> Unit,
) = UnstyledTextField(
	state = state,
	modifier = modifier,
	enabled = editable,
	readOnly = readOnly,
	cursorBrush = cursorBrush,
	textStyle = textStyle,
	textAlign = textAlign,
	lineHeight = lineHeight,
	fontSize = fontSize,
	letterSpacing = letterSpacing,
	fontWeight = fontWeight,
	fontFamily = fontFamily,
	lineLimits = if (singleLine) {
		TextFieldLineLimits.SingleLine
	} else {
		TextFieldLineLimits.MultiLine(
			minHeightInLines = minLines,
			maxHeightInLines = maxLines,
		)
	},
	inputTransformation = inputTransformation,
	outputTransformation = outputTransformation,
	onKeyboardAction = onKeyboardAction,
	keyboardOptions = keyboardOptions,
	interactionSource = interactionSource,
	textColor = textColor,
	scrollState = scrollState,
	content = { TextFieldScope(this).content() },
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
) {
	Surface(
		modifier = modifier,
		shape = shape,
		backgroundColor = backgroundColor,
		contentColor = contentColor,
		contentPadding = contentPadding,
		contentAlignment = if (label == null) Alignment.CenterStart else Alignment.TopStart,
	) {
		Column(
			Modifier.fillMaxWidth(),
			verticalArrangement = Arrangement.spacedBy(if (label == null) 0.dp else YesTheme.sizes.spacingSmall),
		) {
			label?.let {
				Text(
					it,
					style = YesTheme.typography.caption,
					color = YesTheme.colors.textTertiary,
					singleLine = true,
				)
			}
			Row(
				Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(YesTheme.sizes.spacingSmall),
				verticalAlignment = verticalAlignment,
			) {
				leading?.invoke()
				Box(
					Modifier.weight(1f),
					when (verticalAlignment) {
						Alignment.Top    -> Alignment.TopStart
						Alignment.Bottom -> Alignment.BottomStart
						else             -> Alignment.CenterStart
					},
				) {
					with(delegate) {
						UnstyledTextInput(
							modifier = Modifier.fillMaxWidth(),
							placeholder = placeholder,
						)
					}
				}
				trailing?.invoke()
			}
		}
	}
}