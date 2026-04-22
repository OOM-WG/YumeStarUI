@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.widgets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import work.niggergo.yesui.core.theme.YesTheme

@Suppress("unused")
@Composable
fun PrimarySpacer(modifier: Modifier = Modifier) = Spacer(Modifier.size(YesTheme.sizes.spacingPrimary).then(modifier))

@Suppress("unused")
@Composable
fun SecondarySpacer(modifier: Modifier = Modifier) = Spacer(Modifier.size(YesTheme.sizes.spacingSecondary).then(modifier))

@Suppress("unused")
@Composable
fun TertiarySpacer(modifier: Modifier = Modifier) = Spacer(Modifier.size(YesTheme.sizes.spacingTertiary).then(modifier))