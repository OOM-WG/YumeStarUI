@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.composeunstyled.UnstyledTabGroup
import com.composeunstyled.Tab as UnstyledTab
import com.composeunstyled.TabGroupScope as UnstyledTabGroupScope
import com.composeunstyled.TabList as UnstyledTabList
import com.composeunstyled.TabListScope as UnstyledTabListScope
import com.composeunstyled.TabPanel as UnstyledTabPanel
import com.composeunstyled.TabScope as UnstyledTabScope

@Stable
class TabGroupScope<T> internal constructor(internal val delegate: UnstyledTabGroupScope<T>)

@Stable
class TabListScope<T> internal constructor(internal val delegate: UnstyledTabListScope<T>)

@Stable
class TabScope internal constructor(internal val delegate: UnstyledTabScope) {
	val selected get() = delegate.selected
	val enabled get() = delegate.enabled
}

@Suppress("unused")
@Composable
fun <T> TabGroup(
	selectedTab: T,
	onSelectedTabChange: (T) -> Unit,
	tabs: List<T>,
	modifier: Modifier = Modifier,
	verticalArrangement: Arrangement.Vertical = Arrangement.Top,
	horizontalAlignment: Alignment.Horizontal = Alignment.Start,
	content: @Composable TabGroupScope<T>.() -> Unit,
) = UnstyledTabGroup(
	selectedTab = selectedTab,
	onSelectedTabChange = onSelectedTabChange,
	tabs = tabs,
	modifier = modifier,
	content = {
		val tabGroupScope = TabGroupScope(this)
		Column(
			verticalArrangement = verticalArrangement,
			horizontalAlignment = horizontalAlignment,
		) { tabGroupScope.content() }
	},
)

@Suppress("unused")
@Composable
fun <T> TabGroupScope<T>.TabList(
	modifier: Modifier = Modifier,
	orientation: Orientation = Orientation.Horizontal,
	content: @Composable TabListScope<T>.() -> Unit,
) = with(delegate) {
	UnstyledTabList(
		modifier = modifier,
		orientation = orientation,
		content = { TabListScope(this).content() },
	)
}

@Suppress("unused")
@Composable
fun <T> TabListScope<T>.Tab(
	key: T,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	activateOnFocus: Boolean = true,
	indication: Indication? = LocalIndication.current,
	interactionSource: MutableInteractionSource? = null,
	content: @Composable TabScope.() -> Unit,
) = with(delegate) {
	UnstyledTab(
		key = key,
		modifier = modifier,
		enabled = enabled,
		activateOnFocus = activateOnFocus,
		indication = indication,
		interactionSource = interactionSource,
		content = { TabScope(this).content() },
	)
}

@Suppress("unused")
@Composable
fun <T> TabGroupScope<T>.TabPanel(
	key: T,
	modifier: Modifier = Modifier,
	content: @Composable () -> Unit,
) = with(delegate) {
	UnstyledTabPanel(
		key = key,
		modifier = modifier,
		content = content,
	)
}