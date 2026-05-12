@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composeunstyled.Portal as UnstyledPortal
import com.composeunstyled.PortalHost as UnstyledPortalHost

@Suppress("unused")
@Composable
fun PortalHost(modifier: Modifier = Modifier, content: @Composable () -> Unit) =
	UnstyledPortalHost(modifier = modifier, content = content)

@Suppress("unused")
@Composable
fun Portal(content: @Composable () -> Unit) = UnstyledPortal(content = content)