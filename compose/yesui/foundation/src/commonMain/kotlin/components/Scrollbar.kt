@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledHorizontalScrollbar
import com.composeunstyled.UnstyledVerticalScrollbar
import work.niggergo.yesui.foundation.theme.YesTheme
import kotlin.time.Duration
import com.composeunstyled.ScrollbarScope as UnstyledScrollbarScope
import com.composeunstyled.ScrollbarState as UnstyledScrollbarState
import com.composeunstyled.Thumb as UnstyledScrollbarThumb
import com.composeunstyled.ThumbVisibility as UnstyledThumbVisibility
import com.composeunstyled.maxScrollOffset as unstyledMaxScrollOffset
import com.composeunstyled.rememberScrollbarState as rememberUnstyledScrollbarState

@Suppress("unused")
@Stable
class ScrollbarState internal constructor(internal val delegate: UnstyledScrollbarState) {
	val scrollOffset get() = delegate.scrollOffset
	val contentSize get() = delegate.contentSize
	val viewportSize get() = delegate.viewportSize
	val maxScrollOffset get() = delegate.unstyledMaxScrollOffset
	val interactionSource: InteractionSource get() = delegate.interactionSource
	val isScrollInProgress get() = delegate.isScrollInProgress

	suspend fun scrollTo(scrollOffset: Double) = delegate.scrollTo(scrollOffset)
}

@Stable
class ScrollbarScope internal constructor(internal val delegate: UnstyledScrollbarScope)

sealed class ScrollbarThumbVisibility {
	data object AlwaysVisible : ScrollbarThumbVisibility()

	data class HideWhileIdle(
		val enter: EnterTransition,
		val exit: ExitTransition,
		val hideDelay: Duration,
	) : ScrollbarThumbVisibility()
}

@Suppress("unused")
@Composable
fun rememberScrollbarState(scrollState: ScrollState): ScrollbarState {
	val delegate = rememberUnstyledScrollbarState(scrollState)
	return remember(delegate) { ScrollbarState(delegate) }
}

@Suppress("unused")
@Composable
fun rememberScrollbarState(lazyListState: LazyListState): ScrollbarState {
	val delegate = rememberUnstyledScrollbarState(lazyListState)
	return remember(delegate) { ScrollbarState(delegate) }
}

@Suppress("unused")
@Composable
fun rememberScrollbarState(lazyGridState: LazyGridState): ScrollbarState {
	val delegate = rememberUnstyledScrollbarState(lazyGridState)
	return remember(delegate) { ScrollbarState(delegate) }
}

@Suppress("unused")
@Composable
fun VerticalScrollbar(
	state: ScrollbarState,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	interactionSource: MutableInteractionSource? = null,
	reverseLayout: Boolean = false,
	thumb: @Composable ScrollbarScope.() -> Unit = {
		ScrollbarThumb(modifier = Modifier.width(6.dp).heightIn(min = 36.dp))
	},
) = UnstyledVerticalScrollbar(
	scrollbarState = state.delegate,
	modifier = modifier,
	enabled = enabled,
	interactionSource = interactionSource,
	reverseLayout = reverseLayout,
	thumb = { ScrollbarScope(this).thumb() },
)

@Suppress("unused")
@Composable
fun HorizontalScrollbar(
	state: ScrollbarState,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	interactionSource: MutableInteractionSource? = null,
	reverseLayout: Boolean = false,
	thumb: @Composable ScrollbarScope.() -> Unit = {
		ScrollbarThumb(modifier = Modifier.height(6.dp).widthIn(min = 36.dp))
	},
) = UnstyledHorizontalScrollbar(
	scrollbarState = state.delegate,
	modifier = modifier,
	enabled = enabled,
	interactionSource = interactionSource,
	reverseLayout = reverseLayout,
	thumb = { ScrollbarScope(this).thumb() },
)

@Suppress("unused")
@Composable
fun ScrollbarScope.ScrollbarThumb(
	modifier: Modifier = Modifier,
	color: Color = YesTheme.colors.tintVariant,
	shape: Shape = YesTheme.shapes.pill,
	visibility: ScrollbarThumbVisibility = ScrollbarThumbVisibility.AlwaysVisible,
	enabled: Boolean = true,
) = with(delegate) {
	UnstyledScrollbarThumb(
		modifier = modifier.background(
			color = if (enabled) color else YesTheme.colors.disabledBackgroundVariant,
			shape = shape,
		),
		thumbVisibility = visibility.toUnstyled(),
		enabled = enabled,
	)
}

private fun ScrollbarThumbVisibility.toUnstyled() = when (this) {
	ScrollbarThumbVisibility.AlwaysVisible    -> UnstyledThumbVisibility.AlwaysVisible
	is ScrollbarThumbVisibility.HideWhileIdle -> UnstyledThumbVisibility.HideWhileIdle(
		enter = enter,
		exit = exit,
		hideDelay = hideDelay,
	)
}