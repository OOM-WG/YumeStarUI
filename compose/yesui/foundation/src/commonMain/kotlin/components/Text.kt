@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.composeunstyled.LocalTextStyle
import com.composeunstyled.UnstyledText

@Suppress("unused")
@Composable
fun Text(
	text: String,
	modifier: Modifier = Modifier,
	style: TextStyle = LocalTextStyle.current,
	textAlign: TextAlign = TextAlign.Unspecified,
	lineHeight: TextUnit = TextUnit.Unspecified,
	fontSize: TextUnit = style.fontSize,
	letterSpacing: TextUnit = style.letterSpacing,
	fontWeight: FontWeight? = style.fontWeight,
	color: Color = Color.Unspecified,
	fontFamily: FontFamily? = style.fontFamily,
	singleLine: Boolean = false,
	minLines: Int = 1,
	maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
	onTextLayout: ((TextLayoutResult) -> Unit)? = null,
	overflow: TextOverflow = TextOverflow.Clip,
	autoSize: TextAutoSize? = null,
) = UnstyledText(
	text = text,
	modifier = modifier,
	style = style,
	textAlign = textAlign,
	lineHeight = lineHeight,
	fontSize = fontSize,
	letterSpacing = letterSpacing,
	fontWeight = fontWeight,
	color = color,
	fontFamily = fontFamily,
	singleLine = singleLine,
	minLines = minLines,
	maxLines = maxLines,
	onTextLayout = onTextLayout,
	overflow = overflow,
	autoSize = autoSize,
)

@Suppress("unused")
@Composable
fun Text(
	text: AnnotatedString,
	modifier: Modifier = Modifier,
	style: TextStyle = LocalTextStyle.current,
	textAlign: TextAlign = TextAlign.Unspecified,
	fontSize: TextUnit = style.fontSize,
	letterSpacing: TextUnit = style.letterSpacing,
	fontWeight: FontWeight? = style.fontWeight,
	color: Color = Color.Unspecified,
	lineHeight: TextUnit = TextUnit.Unspecified,
	fontFamily: FontFamily? = style.fontFamily,
	singleLine: Boolean = false,
	minLines: Int = 1,
	maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
	onTextLayout: ((TextLayoutResult) -> Unit)? = null,
	overflow: TextOverflow = TextOverflow.Clip,
	autoSize: TextAutoSize? = null,
) = UnstyledText(
	text = text,
	modifier = modifier,
	style = style,
	textAlign = textAlign,
	fontSize = fontSize,
	letterSpacing = letterSpacing,
	fontWeight = fontWeight,
	color = color,
	lineHeight = lineHeight,
	fontFamily = fontFamily,
	singleLine = singleLine,
	minLines = minLines,
	maxLines = maxLines,
	onTextLayout = onTextLayout,
	overflow = overflow,
	autoSize = autoSize,
)