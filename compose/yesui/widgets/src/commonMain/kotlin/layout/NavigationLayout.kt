@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.widgets.layout

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import work.niggergo.yesui.core.theme.YesTheme
import work.niggergo.yesui.core.utils.smoothCapsuleShape
import work.niggergo.yesui.core.utils.smoothShape
import work.niggergo.yesui.core.widgets.Surface

@Suppress("unused")
@Composable
fun NavigationLayout(
	info: ScaffoldNavigationLayoutInfo,
	modifier: Modifier = Modifier,
	sectionSpacing: Dp = 16.dp,
	headerModifier: Modifier = Modifier,
	bodyModifier: Modifier = Modifier,
	footerModifier: Modifier = Modifier,
	footerAlignment: Alignment = Alignment.BottomCenter,
	footerHorizontalInset: Dp = if (info.value == ScaffoldNavigationMode.Expanded) 8.dp else 2.dp,
	footerContainer: Boolean = true,
	footerShape: Shape = defaultNavigationFooterShape(info),
	footerBackgroundColor: Color = Color.Unspecified,
	footerContentColor: Color = Color.Unspecified,
	footerPadding: PaddingValues = defaultNavigationFooterPadding(info),
	footerItemSpacing: Dp = if (info.value == ScaffoldNavigationMode.Expanded) 18.dp else 24.dp,
	footerHorizontalAlignment: Alignment.Horizontal = if (info.value == ScaffoldNavigationMode.Expanded) Alignment.Start else Alignment.CenterHorizontally,
	header: @Composable BoxScope.(ScaffoldNavigationLayoutInfo) -> Unit = {},
	body: @Composable BoxScope.(ScaffoldNavigationLayoutInfo) -> Unit = {},
	footer: (@Composable ColumnScope.(ScaffoldNavigationLayoutInfo) -> Unit)? = null,
) {
	Column(
		modifier = modifier.fillMaxSize(),
	) {
		Box(
			modifier = headerModifier.fillMaxWidth(),
			content = { header(info) },
		)

		Box(
			modifier = bodyModifier.weight(1f, fill = true).fillMaxWidth(),
			content = { body(info) },
		)

		footer?.let {
			Box(
				modifier = footerModifier.fillMaxWidth().padding(horizontal = footerHorizontalInset),
				contentAlignment = footerAlignment,
			) {
				if (footerContainer) NavigationFooterContainer(
					info = info,
					shape = footerShape,
					backgroundColor = footerBackgroundColor,
					contentColor = footerContentColor,
					contentPadding = footerPadding,
					itemSpacing = footerItemSpacing,
					horizontalAlignment = footerHorizontalAlignment,
					content = it,
				)
				else Column(
					verticalArrangement = Arrangement.spacedBy(footerItemSpacing),
					horizontalAlignment = footerHorizontalAlignment,
					content = { it(info) },
				)
			}
		}
	}
}

@Composable
private inline fun NavigationFooterContainer(
	info: ScaffoldNavigationLayoutInfo,
	shape: Shape,
	backgroundColor: Color,
	contentColor: Color,
	contentPadding: PaddingValues,
	itemSpacing: Dp,
	horizontalAlignment: Alignment.Horizontal,
	crossinline content: @Composable ColumnScope.(ScaffoldNavigationLayoutInfo) -> Unit,
) {
	val colors = YesTheme.colors
	val resolvedBackground = backgroundColor.takeIf { it.isSpecified } ?: colors.controlGlass.fillColor

	Surface(
		modifier = Modifier.wrapContentWidth().clip(shape),
		backgroundColor = resolvedBackground,
		contentColor = contentColor.takeIf { it.isSpecified } ?: colors.content,
		contentPadding = contentPadding,
	) {
		Column(
			verticalArrangement = Arrangement.spacedBy(itemSpacing),
			horizontalAlignment = horizontalAlignment,
			content = { content(info) },
		)
	}
}

@Composable
private fun defaultNavigationFooterShape(info: ScaffoldNavigationLayoutInfo) = when (info.value) {
	ScaffoldNavigationMode.Expanded -> smoothShape(YesTheme.shapes.large + 6.dp)
	else                            -> smoothCapsuleShape()
}

private fun defaultNavigationFooterPadding(info: ScaffoldNavigationLayoutInfo) = PaddingValues(
	horizontal = if (info.value == ScaffoldNavigationMode.Expanded) 18.dp else 10.dp,
	vertical = if (info.value == ScaffoldNavigationMode.Expanded) 16.dp else 16.dp,
)