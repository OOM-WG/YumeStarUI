@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composeunstyled.UnstyledDisclosure
import com.composeunstyled.DisclosedContent as UnstyledDisclosedContent
import com.composeunstyled.DisclosureButton as UnstyledDisclosureButton
import com.composeunstyled.DisclosureScope as UnstyledDisclosureScope

@Suppress("unused")
@Stable
class DisclosureScope internal constructor(internal val delegate: UnstyledDisclosureScope) {
	val expanded get() = delegate.expanded
}

@Suppress("unused")
@Composable
fun Disclosure(
	expanded: Boolean,
	onExpandedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
	verticalArrangement: Arrangement.Vertical = Arrangement.Top,
	horizontalAlignment: Alignment.Horizontal = Alignment.Start,
	content: @Composable DisclosureScope.() -> Unit,
) = UnstyledDisclosure(
	expanded = expanded,
	onExpandedChange = onExpandedChange,
	modifier = modifier,
	content = {
		val disclosureScope = DisclosureScope(this)
		Column(
			verticalArrangement = verticalArrangement,
			horizontalAlignment = horizontalAlignment,
		) { disclosureScope.content() }
	},
)

@Suppress("unused")
@Composable
fun DisclosureScope.DisclosureButton(
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	contentPadding: PaddingValues = PaddingValues(0.dp),
	indication: Indication? = LocalIndication.current,
	interactionSource: MutableInteractionSource? = null,
	contentAlignment: Alignment = Alignment.Center,
	content: @Composable () -> Unit,
) = with(delegate) {
	UnstyledDisclosureButton(
		modifier = modifier,
		enabled = enabled,
		contentPadding = contentPadding,
		indication = indication,
		interactionSource = interactionSource,
		contentAlignment = contentAlignment,
		content = content,
	)
}

@Suppress("unused")
@Composable
fun DisclosureScope.DisclosedContent(
	modifier: Modifier = Modifier,
	enter: EnterTransition = EnterTransition.None,
	exit: ExitTransition = ExitTransition.None,
	content: @Composable () -> Unit,
) = with(delegate) {
	UnstyledDisclosedContent(
		modifier = modifier,
		enter = enter,
		exit = exit,
		content = content,
	)
}