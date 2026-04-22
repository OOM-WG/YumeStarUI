@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.core.theme

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.highcapable.betterandroid.system.extension.utils.AndroidVersion

@Composable
internal actual fun platformSystemColors() = with(LocalContext.current) {
	when {
		AndroidVersion.isAtLeast(AndroidVersion.M) -> SystemColors(
			getAccentColor(),
			getThemeColor(android.R.attr.colorBackground),
			getThemeColor(android.R.attr.colorForeground) ?: getThemeColor(android.R.attr.textColorPrimary),
		)

		AndroidVersion.isAtLeast(AndroidVersion.L) -> SystemColors(getAccentColor())
		else                                       -> null
	}?.takeUnless { it.accentColor == null && it.backgroundColor == null && it.foregroundColor == null }
}

@RequiresApi(AndroidVersion.L)
private fun Context.getAccentColor() = AndroidVersion.requireOrNull(
	AndroidVersion.S, TypedValue().takeIf { theme.resolveAttribute(android.R.attr.colorAccent, it, true) }?.data
) { resources.getColor(android.R.color.system_accent1_500, theme) }?.let { Color(it) }

@RequiresApi(AndroidVersion.M)
private fun Context.getThemeColor(@AttrRes attr: Int) =
	TypedValue().takeIf { theme.resolveAttribute(attr, it, true) }?.runCatching {
		when {
			resourceId != 0                                                         -> Color(
				resources.getColor(resourceId, theme)
			)

			type in TypedValue.TYPE_FIRST_COLOR_INT..TypedValue.TYPE_LAST_COLOR_INT -> Color(data)
			else                                                                    -> null
		}
	}?.getOrNull()