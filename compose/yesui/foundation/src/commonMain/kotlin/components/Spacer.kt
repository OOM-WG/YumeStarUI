@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.foundation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import work.niggergo.yesui.foundation.theme.YesTheme

@Suppress("unused")
@Composable
fun ExtraLargeSpacer(modifier: Modifier = Modifier) = Spacer(Modifier.size(YesTheme.sizes.spacingExtraLarge).then(modifier))

@Suppress("unused")
@Composable
fun LargeSpacer(modifier: Modifier = Modifier) = Spacer(Modifier.size(YesTheme.sizes.spacingLarge).then(modifier))

@Suppress("unused")
@Composable
fun MediumSpacer(modifier: Modifier = Modifier) = Spacer(Modifier.size(YesTheme.sizes.spacingMedium).then(modifier))