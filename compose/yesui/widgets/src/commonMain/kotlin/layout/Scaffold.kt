@file:Suppress("PackageDirectoryMismatch")

package work.niggergo.yesui.widgets.layout

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.lerp
import com.composeunstyled.buildModifier
import kotlinx.coroutines.launch
import top.yukonga.miuix.kmp.blur.*
import work.niggergo.yesui.core.theme.*
import work.niggergo.yesui.core.utils.*
import work.niggergo.yesui.core.widgets.*
import work.niggergo.yesui.icons.YesBasicIcons
import work.niggergo.yesui.widgets.utils.*
import kotlin.math.abs
import kotlin.math.roundToInt

enum class ScaffoldNavigationMode { Hidden, Rail, Expanded }

@PublishedApi
internal enum class ScaffoldLayoutMode { Compact, Expanded }

private enum class ScaffoldWidthBand { Narrow, Intermediate, Wide }

internal data class ScaffoldTrack(
	val hiddenPx: Float,
	val railPx: Float,
	val expandedPx: Float,
)

private data class ScaffoldResponsiveSpec(
	val railWidthRatio: Float,
	val expandedWidthRatio: Float,
	val railInsetRatioEach: Float,
	val expandedInsetRatioEach: Float,
)

private data class ScaffoldPageLayoutSpec(
	val hiddenInnerPaddingPx: Float,
	val railInnerPaddingPx: Float,
	val expandedInnerPaddingPx: Float,
	val railOffsetPx: Float,
	val expandedOffsetPx: Float,
	val expandedTrailingRevealPx: Float = 0f,
)

@PublishedApi
internal data class ScaffoldResolvedNavigation(
	val railWidthPx: Float,
	val expandedWidthPx: Float,
	val railContentInsetPx: Float,
	val expandedContentInsetPx: Float,
)

@PublishedApi
internal data class ScaffoldPageMetrics(
	val contentWidthPx: Float,
	val startTrack: ScaffoldTrack,
	val widthTrack: ScaffoldTrack,
)

@PublishedApi
internal data class ScaffoldGlassStyle(
	val blurStyle: YesBlurStyle,
	val gradientStart: Color,
	val gradientEnd: Color,
)

@PublishedApi
internal data class ScaffoldPalette(
	val navigation: ScaffoldGlassStyle,
	val button: ScaffoldGlassStyle,
	val pageSurfaceColor: Color,
)

@Immutable
data class ScaffoldNavigationLayoutInfo(
	val value: ScaffoldNavigationMode,
	val width: Dp,
	val safeContainerWidth: Dp,
	val safeWidth: Dp,
	val progress: Float,
	val isExpandedLayout: Boolean,
)

@Suppress("unused")
@Stable
class ScaffoldState internal constructor(initialValue: ScaffoldNavigationMode?) {
	var currentValue by mutableStateOf(initialValue ?: ScaffoldNavigationMode.Rail)
		private set
	private var hasResolvedInitialValue by mutableStateOf(initialValue != null)

	fun snapTo(value: ScaffoldNavigationMode) {
		currentValue = value
		hasResolvedInitialValue = true
	}

	fun hide() = snapTo(ScaffoldNavigationMode.Hidden)
	fun rail() = snapTo(ScaffoldNavigationMode.Rail)
	fun expand() = snapTo(ScaffoldNavigationMode.Expanded)

	@PublishedApi
	internal fun resolveInitial(value: ScaffoldNavigationMode) {
		if (!hasResolvedInitialValue) {
			currentValue = value
			hasResolvedInitialValue = true
		}
	}
}

@Composable
fun rememberScaffoldState(initialValue: ScaffoldNavigationMode? = null) = remember { ScaffoldState(initialValue) }

@Suppress("unused")
@Composable
inline fun Scaffold(
	modifier: Modifier = Modifier,
	state: ScaffoldState = rememberScaffoldState(),
	autoExpandAspectRatio: Float = 1.2f,
	autoExpandMinWidth: Dp = 720.dp,
	hiddenWidth: Dp = 0.dp,
	railWidth: Dp = 76.dp,
	expandedWidth: Dp = 280.dp,
	contentOverlap: Dp = 28.dp,
	dragHandleWidth: Dp = 96.dp,
	windowPadding: PaddingValues = PaddingValues(),
	pageContentPadding: PaddingValues = PaddingValues(
		start = 24.dp,
		top = 72.dp,
		end = 24.dp,
		bottom = 24.dp,
	),
	navigationPadding: PaddingValues = PaddingValues(
		horizontal = 12.dp,
		vertical = 20.dp,
	),
	noinline navigationContent: @Composable ColumnScope.(ScaffoldNavigationLayoutInfo) -> Unit = {},
	crossinline content: @Composable BoxScope.(PaddingValues) -> Unit,
) = BoxWithConstraints(modifier.fillMaxSize()) {
	val toggleButtonSize = 52.dp
	val toggleButtonOuterPadding = 16.dp
	val navigationTopSpacing = 12.dp
	val navigationHeaderClearance = toggleButtonSize + toggleButtonOuterPadding + navigationTopSpacing
	val compactRailPageGap = 4.dp
	val wideRailPageGap = 28.dp
	val colors = YesTheme.colors
	val palette = rememberScaffoldPalette(colors)
	val background = YesTheme.background
	val layoutDirection = LocalLayoutDirection.current
	val density = LocalDensity.current
	val maxWidthPx = with(density) { maxWidth.toPx() }
	val startInsetPx = with(density) { windowPadding.calculateLeftPadding(layoutDirection).toPx() }
	val endInsetPx = with(density) { windowPadding.calculateRightPadding(layoutDirection).toPx() }
	val topInset = windowPadding.calculateTopPadding()
	val shapeRadiusPx = with(density) { YesTheme.shapes.large.toPx() }

	val shouldStartExpanded =
		maxHeight > 0.dp && maxWidth >= autoExpandMinWidth && maxWidth.value / maxHeight.value >= autoExpandAspectRatio
	val layoutMode = if (shouldStartExpanded) ScaffoldLayoutMode.Expanded else ScaffoldLayoutMode.Compact
	LaunchedEffect(shouldStartExpanded) {
		state.resolveInitial(if (shouldStartExpanded) ScaffoldNavigationMode.Expanded else ScaffoldNavigationMode.Rail)
	}

	val backdrop = rememberLayerBackdrop()
	val shouldUseBlur = background is YesBackground.GradientBrush || background is YesBackground.PainterBackground

	val hiddenWidthPx = with(density) { hiddenWidth.toPx() }
	val minRailWidthPx = with(density) { railWidth.toPx() }
	val minExpandedWidthPx = with(density) { expandedWidth.toPx() }
	val overlapPx = with(density) { contentOverlap.toPx() }
	val coroutineScope = rememberCoroutineScope()
	val availablePageWidthPx = (maxWidthPx - startInsetPx - endInsetPx).coerceAtLeast(0f)
	val aspectRatio = if (maxHeight > 0.dp) maxWidth.value / maxHeight.value else 1f
	val navigationMetrics = remember(
		aspectRatio,
		availablePageWidthPx,
		minRailWidthPx,
		minExpandedWidthPx,
	) {
		resolveNavigationMetrics(
			aspectRatio = aspectRatio,
			availablePageWidthPx = availablePageWidthPx,
			minRailWidthPx = minRailWidthPx,
			minExpandedWidthPx = minExpandedWidthPx,
		)
	}
	val railWidthPx = navigationMetrics.railWidthPx
	val expandedWidthPx = navigationMetrics.expandedWidthPx
	val railContentInsetPx = navigationMetrics.railContentInsetPx
	val expandedContentInsetPx = navigationMetrics.expandedContentInsetPx

	var dragWidthPx by remember { mutableStateOf(0f) }
	var isDragging by remember { mutableStateOf(false) }
	val targetNavigationWidthPx = navigationWidthFor(state.currentValue, hiddenWidthPx, railWidthPx, expandedWidthPx)
	val navigationWidthPx = remember(hiddenWidthPx, railWidthPx, expandedWidthPx) {
		Animatable(targetNavigationWidthPx)
	}
	LaunchedEffect(targetNavigationWidthPx, isDragging) {
		if (isDragging) return@LaunchedEffect
		if (abs(navigationWidthPx.value - targetNavigationWidthPx) <= 0.5f) {
			navigationWidthPx.snapTo(targetNavigationWidthPx)
		} else {
			navigationWidthPx.animateTo(
				targetValue = targetNavigationWidthPx,
				animationSpec = spring(
					dampingRatio = Spring.DampingRatioNoBouncy,
					stiffness = Spring.StiffnessMediumLow,
				),
			)
		}
	}
	val currentNavigationWidthPx = if (isDragging) dragWidthPx else navigationWidthPx.value

	val snapToValue = remember(state, navigationWidthPx) {
		{ value: ScaffoldNavigationMode ->
			isDragging = false
			dragWidthPx = navigationWidthPx.value
			state.snapTo(value)
		}
	}
	val draggedNavigationValue = remember(
		currentNavigationWidthPx,
		hiddenWidthPx,
		railWidthPx,
		expandedWidthPx,
	) {
		displayedValueFor(
			widthPx = currentNavigationWidthPx,
			hiddenWidthPx = hiddenWidthPx,
			railWidthPx = railWidthPx,
			expandedWidthPx = expandedWidthPx,
		)
	}
	val controlNavigationValue = if (isDragging) draggedNavigationValue else state.currentValue
	val contentNavigationValue = controlNavigationValue.normalizeContentValue()
	val displayedNavigationWidth = with(density) {
		displayedWidthFor(
			value = controlNavigationValue,
			railWidthPx = railWidthPx,
			expandedWidthPx = expandedWidthPx,
		).toDp()
	}
	val pageContentStartPaddingPx = with(density) { pageContentPadding.calculateStartPadding(layoutDirection).toPx() }
	val pageContentEndPaddingPx = with(density) { pageContentPadding.calculateEndPadding(layoutDirection).toPx() }
	val pageMetrics = remember(
		layoutMode,
		density,
		startInsetPx,
		availablePageWidthPx,
		railWidthPx,
		expandedWidthPx,
		overlapPx,
		pageContentStartPaddingPx,
		pageContentEndPaddingPx,
		compactRailPageGap,
		wideRailPageGap,
	) {
		resolvePageMetrics(
			layoutMode = layoutMode,
			density = density,
			startInsetPx = startInsetPx,
			availablePageWidthPx = availablePageWidthPx,
			railWidthPx = railWidthPx,
			expandedWidthPx = expandedWidthPx,
			overlapPx = overlapPx,
			pageContentStartPaddingPx = pageContentStartPaddingPx,
			pageContentEndPaddingPx = pageContentEndPaddingPx,
			compactRailPageGap = compactRailPageGap,
			wideRailPageGap = wideRailPageGap,
		)
	}
	val startTrack = pageMetrics.startTrack
	val widthTrack = pageMetrics.widthTrack
	val pageStartPx = startTrack.resolve(currentNavigationWidthPx, hiddenWidthPx, railWidthPx, expandedWidthPx)
	val intrinsicPageWidthPx = widthTrack.resolve(
		navigationWidthPx = currentNavigationWidthPx,
		hiddenWidthPx = hiddenWidthPx,
		railWidthPx = railWidthPx,
		expandedWidthPx = expandedWidthPx,
	)
	val contentWidthPx = pageMetrics.contentWidthPx
	val pageVisibleWidthPx = (maxWidthPx - endInsetPx - pageStartPx).coerceAtLeast(0f)
	val pageWidthPx = maxOf(intrinsicPageWidthPx, pageVisibleWidthPx)
	val pageWidth = with(density) { pageWidthPx.toDp() }
	val contentWidth = with(density) { contentWidthPx.toDp() }
	val navigationVisualWidthPx = maxOf(
		expandedWidthPx.takeIf { it > 0.5f } ?: railWidthPx,
		expandedWidthPx + overlapPx + shapeRadiusPx,
	)
	val navigationVisualWidth = with(density) { navigationVisualWidthPx.toDp() }
	val navigationSafeTrailingPadding = with(density) { (overlapPx + shapeRadiusPx).toDp() }
	val navigationAlpha = if (currentNavigationWidthPx <= 0.5f) 0f else 1f
	val navigationProgress = if (expandedWidthPx <= 0f) 0f
	else (currentNavigationWidthPx / expandedWidthPx).coerceIn(0f, 1f)

	val navigationContainerWidthPx = navigationContainerWidthFor(
		value = contentNavigationValue,
		railWidthPx = railWidthPx,
		expandedWidthPx = expandedWidthPx,
		railTrailingGapPx = shapeRadiusPx,
		expandedTrailingGapPx = overlapPx + shapeRadiusPx,
	)
	val navigationContainerWidth = with(density) { navigationContainerWidthPx.toDp() }
	val navigationContentWidth = with(density) {
		navigationContentWidthFor(
			value = contentNavigationValue,
			railWidthPx = railWidthPx,
			expandedWidthPx = expandedWidthPx,
			railInsetPx = railContentInsetPx,
			expandedInsetPx = expandedContentInsetPx,
		).coerceAtMost(navigationContainerWidthPx).toDp()
	}
	val navigationInfo = remember(
		contentNavigationValue,
		displayedNavigationWidth,
		navigationContainerWidth,
		navigationContentWidth,
		navigationProgress,
		shouldStartExpanded,
	) {
		ScaffoldNavigationLayoutInfo(
			value = contentNavigationValue,
			width = displayedNavigationWidth,
			safeContainerWidth = navigationContainerWidth,
			safeWidth = navigationContentWidth,
			progress = navigationProgress,
			isExpandedLayout = shouldStartExpanded,
		)
	}
	val resolvedNavigationPadding = remember(windowPadding, navigationPadding, layoutDirection) {
		PaddingValues(
			start = windowPadding.calculateLeftPadding(layoutDirection) + navigationPadding.calculateLeftPadding(
				layoutDirection
			),
			top = topInset + navigationPadding.calculateTopPadding(),
			end = windowPadding.calculateRightPadding(layoutDirection) + navigationPadding.calculateRightPadding(
				layoutDirection
			),
			bottom = windowPadding.calculateBottomPadding() + navigationPadding.calculateBottomPadding(),
		)
	}
	val toggleButtonModifier = Modifier.align(Alignment.TopStart).padding(
		start = windowPadding.calculateLeftPadding(layoutDirection) + toggleButtonOuterPadding,
		top = topInset + toggleButtonOuterPadding,
	)

	Box(Modifier.fillMaxSize()) {
		Box(Modifier.fillMaxSize().background(colors.background).layerBackdrop(backdrop)) {
			Background(Modifier.fillMaxSize(), background)
		}

		if (navigationAlpha > 0.001f) NavigationPanel(
			width = navigationVisualWidth,
			info = navigationInfo,
			style = palette.navigation,
			padding = resolvedNavigationPadding,
			topClearance = navigationHeaderClearance,
			safeTrailingPadding = navigationSafeTrailingPadding,
			backdrop = backdrop,
			useBlur = shouldUseBlur,
			content = navigationContent,
		)

		PageContent(
			width = pageWidth,
			contentWidth = contentWidth,
			startOffsetPx = pageStartPx,
			hasNavigation = currentNavigationWidthPx > 0.5f,
			layoutDirection = layoutDirection,
			surfaceColor = palette.pageSurfaceColor,
		) { content(pageContentPadding) }

		ToggleButton(
			modifier = toggleButtonModifier,
			value = controlNavigationValue,
			style = palette.button,
			backdrop = backdrop,
			useBlur = shouldUseBlur,
			onHide = { snapToValue(ScaffoldNavigationMode.Hidden) },
			onRail = { snapToValue(ScaffoldNavigationMode.Rail) },
			onExpand = { snapToValue(ScaffoldNavigationMode.Expanded) },
		)

		DragHandle(
			width = dragHandleWidth,
			startOffsetPx = pageStartPx,
			layoutDirection = layoutDirection,
			topPadding = topInset + navigationHeaderClearance,
			density = density,
			onDragStart = {
				isDragging = true
				dragWidthPx = navigationWidthPx.value
				coroutineScope.launch {
					navigationWidthPx.stop()
					navigationWidthPx.snapTo(dragWidthPx)
				}
			},
			onDragDelta = { delta ->
				val delta = if (layoutDirection == LayoutDirection.Ltr) delta else -delta
				dragWidthPx = dragWidthPx.plus(delta).coerceIn(hiddenWidthPx, expandedWidthPx)
				coroutineScope.launch { navigationWidthPx.snapTo(dragWidthPx) }
			},
			onDragEnd = {
				if (isDragging) snapToValue(nearestValue(dragWidthPx, hiddenWidthPx, railWidthPx, expandedWidthPx))
				else dragWidthPx = navigationWidthPx.value
			},
		)
	}
}

@PublishedApi
@Composable
internal inline fun BoxScope.NavigationPanel(
	width: Dp,
	info: ScaffoldNavigationLayoutInfo,
	style: ScaffoldGlassStyle,
	padding: PaddingValues,
	topClearance: Dp,
	safeTrailingPadding: Dp,
	backdrop: LayerBackdrop,
	useBlur: Boolean,
	content: @Composable ColumnScope.(ScaffoldNavigationLayoutInfo) -> Unit,
) {
	val layoutDirection = LocalLayoutDirection.current
	val blurSupported = useBlur && isRenderEffectSupported()
	val blurColors = style.blurColors()

	Box(
		Modifier.align(Alignment.CenterStart).fillMaxHeight().width(width).then(
			buildModifier {
				if (blurSupported) add(
					Modifier.textureBlur(
						backdrop = backdrop,
						shape = RectangleShape,
						blurRadius = 52f,
						noiseCoefficient = 0f,
						colors = blurColors,
						enabled = true,
					)
				)
			},
		),
	) {
		Box(
			Modifier.matchParentSize().background(
				Brush.horizontalGradient(
					listOf(
						style.gradientStart,
						style.gradientEnd,
					)
				)
			)
		)

		val startPadding = padding.calculateLeftPadding(layoutDirection)
		val endPadding = padding.calculateRightPadding(layoutDirection)
		val safeStartPadding = if (layoutDirection == LayoutDirection.Rtl) safeTrailingPadding else 0.dp
		val safeEndPadding = if (layoutDirection == LayoutDirection.Ltr) safeTrailingPadding else 0.dp

		Box(
			Modifier.fillMaxSize().padding(
				start = startPadding + safeStartPadding,
				top = padding.calculateTopPadding() + topClearance,
				end = endPadding + safeEndPadding,
				bottom = padding.calculateBottomPadding(),
			),
		) {
			Box(
				Modifier.align(
					if (layoutDirection == LayoutDirection.Ltr) Alignment.TopStart else Alignment.TopEnd,
				).width(info.safeContainerWidth).fillMaxHeight(),
			) {
				Column(
					Modifier.align(Alignment.TopCenter).width(info.safeWidth).fillMaxHeight(),
					Arrangement.spacedBy(12.dp),
					content = { content(info) },
				)
			}
		}
	}
}

@PublishedApi
@Composable
internal fun BoxScope.PageContent(
	width: Dp,
	contentWidth: Dp,
	startOffsetPx: Float,
	hasNavigation: Boolean,
	layoutDirection: LayoutDirection,
	surfaceColor: Color,
	content: @Composable BoxScope.() -> Unit,
) {
	val shape = rememberPageShape(
		layoutDirection = layoutDirection,
		radius = YesTheme.shapes.large,
		enabled = hasNavigation,
	)
	val directionSign = if (layoutDirection == LayoutDirection.Ltr) 1 else -1

	Surface(
		Modifier.align(if (layoutDirection == LayoutDirection.Ltr) Alignment.TopStart else Alignment.TopEnd).width(width)
			.fillMaxHeight().offset { IntOffset((startOffsetPx * directionSign).roundToInt(), 0) }.clip(shape),
		backgroundColor = surfaceColor,
		contentColor = YesTheme.colors.content,
		contentPadding = PaddingValues(),
		propagateMinConstraints = true,
	) {
		Box(Modifier.fillMaxSize()) {
			Box(
				Modifier.align(Alignment.TopCenter).width(contentWidth).fillMaxHeight(),
				content = content,
			)
		}
	}
}

@PublishedApi
@Composable
internal fun ToggleButton(
	modifier: Modifier,
	value: ScaffoldNavigationMode,
	style: ScaffoldGlassStyle,
	backdrop: LayerBackdrop,
	useBlur: Boolean,
	onHide: () -> Unit,
	onRail: () -> Unit,
	onExpand: () -> Unit,
) {
	val colors = YesTheme.colors
	val blurSupported = useBlur && isRenderEffectSupported()
	val buttonShape = smoothCapsuleShape()
	val iconPainter = when (value) {
		ScaffoldNavigationMode.Hidden   -> YesBasicIcons.Menu
		ScaffoldNavigationMode.Rail     -> YesBasicIcons.ArrowLeftRight
		ScaffoldNavigationMode.Expanded -> YesBasicIcons.ArrowLeft
	}
	val blurColors = style.blurColors()

	Box(
		modifier.size(52.dp).shadow(12.dp, buttonShape, clip = false).clip(buttonShape).then(
			buildModifier {
				if (blurSupported) add(
					Modifier.textureBlur(
						backdrop = backdrop,
						shape = buttonShape,
						blurRadius = 64f,
						noiseCoefficient = 0f,
						colors = blurColors,
						enabled = true,
					),
				)
			},
		).background(
			Brush.linearGradient(listOf(style.gradientStart, style.gradientEnd)),
			buttonShape,
		),
		Alignment.Center,
	) {
		Icon(
			iconPainter,
			size = YesTheme.sizes.icon,
			tint = colors.content,
		)

		when (value) {
			ScaffoldNavigationMode.Hidden   -> Box(Modifier.fillMaxSize().rippleClickable(onClick = onRail))

			ScaffoldNavigationMode.Rail     -> Row(Modifier.fillMaxSize()) {
				Box(Modifier.weight(1f).fillMaxHeight().rippleClickable(onClick = onHide))
				Box(Modifier.weight(1f).fillMaxHeight().rippleClickable(onClick = onExpand))
			}

			ScaffoldNavigationMode.Expanded -> Box(Modifier.fillMaxSize().rippleClickable(onClick = onRail))
		}
	}
}

@PublishedApi
@Composable
internal fun DragHandle(
	width: Dp,
	startOffsetPx: Float,
	layoutDirection: LayoutDirection,
	topPadding: Dp,
	density: Density,
	onDragStart: () -> Unit,
	onDragDelta: (Float) -> Unit,
	onDragEnd: () -> Unit,
) {
	val widthPx = with(density) { width.toPx() }
	val latestStartOffsetPx by rememberUpdatedState(startOffsetPx)
	val latestLayoutDirection by rememberUpdatedState(layoutDirection)
	val latestOnDragStart by rememberUpdatedState(onDragStart)
	val latestOnDragDelta by rememberUpdatedState(onDragDelta)
	val latestOnDragEnd by rememberUpdatedState(onDragEnd)

	Box(Modifier.fillMaxHeight().padding(top = topPadding).fillMaxWidth().pointerInput(Unit) {
		var isDraggingHandle = false
		detectHorizontalDragGestures(
			onDragStart = { offset ->
				val edgeX = when (latestLayoutDirection) {
					LayoutDirection.Ltr -> latestStartOffsetPx
					LayoutDirection.Rtl -> size.width - latestStartOffsetPx
				}
				isDraggingHandle = abs(offset.x - edgeX) <= widthPx
				if (isDraggingHandle) latestOnDragStart()
			},
			onHorizontalDrag = { change, dragAmount ->
				if (isDraggingHandle) {
					change.consume()
					latestOnDragDelta(dragAmount)
				}
			},
			onDragEnd = {
				if (isDraggingHandle) latestOnDragEnd()
				isDraggingHandle = false
			},
			onDragCancel = {
				if (isDraggingHandle) latestOnDragEnd()
				isDraggingHandle = false
			},
		)
	})
}

@Composable
private fun rememberPageShape(
	layoutDirection: LayoutDirection,
	radius: Dp,
	enabled: Boolean,
) = if (!enabled) {
	RectangleShape
} else {
	when (layoutDirection) {
		LayoutDirection.Ltr -> smoothUnevenShape(topStart = radius, bottomStart = radius)
		LayoutDirection.Rtl -> smoothUnevenShape(topEnd = radius, bottomEnd = radius)
	}
}

@PublishedApi
internal fun nearestValue(
	widthPx: Float,
	hiddenWidthPx: Float,
	railWidthPx: Float,
	expandedWidthPx: Float,
): ScaffoldNavigationMode {
	val hiddenDistance = abs(widthPx - hiddenWidthPx)
	val railDistance = abs(widthPx - railWidthPx)
	val expandedDistance = abs(widthPx - expandedWidthPx)

	return when (minOf(hiddenDistance, railDistance, expandedDistance)) {
		hiddenDistance -> ScaffoldNavigationMode.Hidden
		railDistance   -> ScaffoldNavigationMode.Rail
		else           -> ScaffoldNavigationMode.Expanded
	}
}

@PublishedApi
internal fun displayedValueFor(
	widthPx: Float,
	hiddenWidthPx: Float,
	railWidthPx: Float,
	expandedWidthPx: Float,
): ScaffoldNavigationMode {
	val hiddenToRailCutoff = (hiddenWidthPx + railWidthPx) / 2f
	val railToExpandedCutoff = (railWidthPx + expandedWidthPx) / 2f

	return when {
		widthPx <= hiddenToRailCutoff  -> ScaffoldNavigationMode.Hidden
		widthPx < railToExpandedCutoff -> ScaffoldNavigationMode.Rail
		else                           -> ScaffoldNavigationMode.Expanded
	}
}

@PublishedApi
internal fun displayedWidthFor(
	value: ScaffoldNavigationMode,
	railWidthPx: Float,
	expandedWidthPx: Float,
) = when (value) {
	ScaffoldNavigationMode.Hidden, ScaffoldNavigationMode.Rail -> railWidthPx
	ScaffoldNavigationMode.Expanded                            -> expandedWidthPx
}

@PublishedApi
internal fun navigationWidthFor(
	value: ScaffoldNavigationMode,
	hiddenWidthPx: Float,
	railWidthPx: Float,
	expandedWidthPx: Float,
) = when (value) {
	ScaffoldNavigationMode.Hidden   -> hiddenWidthPx
	ScaffoldNavigationMode.Rail     -> railWidthPx
	ScaffoldNavigationMode.Expanded -> expandedWidthPx
}

@PublishedApi
internal fun navigationContentWidthFor(
	value: ScaffoldNavigationMode,
	railWidthPx: Float,
	expandedWidthPx: Float,
	railInsetPx: Float,
	expandedInsetPx: Float,
) = when (value) {
	ScaffoldNavigationMode.Hidden   -> 0f
	ScaffoldNavigationMode.Rail     -> (railWidthPx - railInsetPx).coerceAtLeast(0f)
	ScaffoldNavigationMode.Expanded -> (expandedWidthPx - expandedInsetPx).coerceAtLeast(railWidthPx - railInsetPx)
}

@PublishedApi
internal fun navigationContainerWidthFor(
	value: ScaffoldNavigationMode,
	railWidthPx: Float,
	expandedWidthPx: Float,
	railTrailingGapPx: Float,
	expandedTrailingGapPx: Float,
) = when (value) {
	ScaffoldNavigationMode.Hidden   -> (railWidthPx - railTrailingGapPx).coerceAtLeast(0f)
	ScaffoldNavigationMode.Rail     -> (railWidthPx - railTrailingGapPx).coerceAtLeast(0f)
	ScaffoldNavigationMode.Expanded -> (expandedWidthPx - expandedTrailingGapPx).coerceAtLeast(railWidthPx)
}

@PublishedApi
internal fun ScaffoldNavigationMode.normalizeContentValue() = when (this) {
	ScaffoldNavigationMode.Hidden -> ScaffoldNavigationMode.Rail
	else                          -> this
}

@PublishedApi
internal fun ScaffoldTrack.resolve(
	navigationWidthPx: Float,
	hiddenWidthPx: Float,
	railWidthPx: Float,
	expandedWidthPx: Float,
) = valueForNavigationWidth(
	navigationWidthPx = navigationWidthPx,
	hiddenWidthPx = hiddenWidthPx,
	railWidthPx = railWidthPx,
	expandedWidthPx = expandedWidthPx,
	hiddenValuePx = hiddenPx,
	railValuePx = railPx,
	expandedValuePx = expandedPx,
)

private fun valueForNavigationWidth(
	navigationWidthPx: Float,
	hiddenWidthPx: Float,
	railWidthPx: Float,
	expandedWidthPx: Float,
	hiddenValuePx: Float,
	railValuePx: Float,
	expandedValuePx: Float,
) = when {
	navigationWidthPx <= railWidthPx -> {
		val fraction = if (railWidthPx - hiddenWidthPx <= 0f) 0f
		else ((navigationWidthPx - hiddenWidthPx) / (railWidthPx - hiddenWidthPx)).coerceIn(0f, 1f)
		lerp(hiddenValuePx, railValuePx, fraction)
	}

	else                             -> {
		val fraction = if (expandedWidthPx - railWidthPx <= 0f) 0f
		else ((navigationWidthPx - railWidthPx) / (expandedWidthPx - railWidthPx)).coerceIn(0f, 1f)
		lerp(railValuePx, expandedValuePx, fraction)
	}
}

private fun widthBandFor(aspectRatio: Float) = when {
	aspectRatio < 0.70f  -> ScaffoldWidthBand.Narrow
	aspectRatio <= 1.25f -> ScaffoldWidthBand.Intermediate
	else                 -> ScaffoldWidthBand.Wide
}

private fun responsiveSpecFor(band: ScaffoldWidthBand) = when (band) {
	ScaffoldWidthBand.Narrow       -> ScaffoldResponsiveSpec(
		railWidthRatio = 0.18f,
		expandedWidthRatio = 0.60f,
		railInsetRatioEach = 0.06f,
		expandedInsetRatioEach = 0.07f,
	)

	ScaffoldWidthBand.Intermediate -> ScaffoldResponsiveSpec(
		railWidthRatio = 0.12f,
		expandedWidthRatio = 0.40f,
		railInsetRatioEach = 0.08f,
		expandedInsetRatioEach = 0.08f,
	)

	ScaffoldWidthBand.Wide         -> ScaffoldResponsiveSpec(
		railWidthRatio = 0.09f,
		expandedWidthRatio = 0.25f,
		railInsetRatioEach = 0.09f,
		expandedInsetRatioEach = 0.09f,
	)
}

@PublishedApi
internal fun resolveNavigationMetrics(
	aspectRatio: Float,
	availablePageWidthPx: Float,
	minRailWidthPx: Float,
	minExpandedWidthPx: Float,
): ScaffoldResolvedNavigation {
	val responsiveSpec = responsiveSpecFor(widthBandFor(aspectRatio))
	val railWidthPx = maxOf(minRailWidthPx, availablePageWidthPx * responsiveSpec.railWidthRatio)
	val expandedWidthPx =
		maxOf(maxOf(railWidthPx, minExpandedWidthPx), availablePageWidthPx * responsiveSpec.expandedWidthRatio)

	return ScaffoldResolvedNavigation(
		railWidthPx = railWidthPx,
		expandedWidthPx = expandedWidthPx,
		railContentInsetPx = (railWidthPx * responsiveSpec.railInsetRatioEach * 2f).coerceIn(0f, railWidthPx),
		expandedContentInsetPx = (expandedWidthPx * responsiveSpec.expandedInsetRatioEach * 2f).coerceIn(0f, expandedWidthPx),
	)
}

@PublishedApi
internal fun resolvePageMetrics(
	layoutMode: ScaffoldLayoutMode,
	density: Density,
	startInsetPx: Float,
	availablePageWidthPx: Float,
	railWidthPx: Float,
	expandedWidthPx: Float,
	overlapPx: Float,
	pageContentStartPaddingPx: Float,
	pageContentEndPaddingPx: Float,
	compactRailPageGap: Dp,
	wideRailPageGap: Dp,
): ScaffoldPageMetrics {
	val minContentWidthPx = pageContentStartPaddingPx + pageContentEndPaddingPx

	val compactSpec = with(density) {
		ScaffoldPageLayoutSpec(
			hiddenInnerPaddingPx = 36.dp.toPx(),
			railInnerPaddingPx = 12.dp.toPx(),
			expandedInnerPaddingPx = 12.dp.toPx(),
			railOffsetPx = railWidthPx + compactRailPageGap.toPx(),
			expandedOffsetPx = expandedWidthPx - overlapPx + 8.dp.toPx(),
			expandedTrailingRevealPx = -184.dp.toPx(),
		)
	}
	val wideSpec = with(density) {
		ScaffoldPageLayoutSpec(
			hiddenInnerPaddingPx = 88.dp.toPx(),
			railInnerPaddingPx = 52.dp.toPx(),
			expandedInnerPaddingPx = 20.dp.toPx(),
			railOffsetPx = maxOf(72.dp.toPx(), railWidthPx + wideRailPageGap.toPx()),
			expandedOffsetPx = expandedWidthPx - overlapPx + 8.dp.toPx(),
			expandedTrailingRevealPx = 72.dp.toPx(),
		)
	}

	return resolvePageMetricsForSpec(
		startInsetPx = startInsetPx,
		availablePageWidthPx = availablePageWidthPx,
		minContentWidthPx = minContentWidthPx,
		spec = if (layoutMode == ScaffoldLayoutMode.Expanded) wideSpec else compactSpec,
	)
}

private fun resolvePageMetricsForSpec(
	startInsetPx: Float,
	availablePageWidthPx: Float,
	minContentWidthPx: Float,
	spec: ScaffoldPageLayoutSpec,
): ScaffoldPageMetrics {
	val contentWidthPx = minOf(
		availablePageWidthPx - (2f * spec.hiddenInnerPaddingPx),
		availablePageWidthPx - spec.railOffsetPx - (2f * spec.railInnerPaddingPx),
		availablePageWidthPx - spec.expandedOffsetPx - spec.expandedTrailingRevealPx - (2f * spec.expandedInnerPaddingPx),
	).coerceAtLeast(minContentWidthPx)

	return ScaffoldPageMetrics(
		contentWidthPx = contentWidthPx,
		startTrack = ScaffoldTrack(
			hiddenPx = startInsetPx,
			railPx = startInsetPx + spec.railOffsetPx,
			expandedPx = startInsetPx + spec.expandedOffsetPx,
		),
		widthTrack = ScaffoldTrack(
			hiddenPx = contentWidthPx + (2f * spec.hiddenInnerPaddingPx),
			railPx = contentWidthPx + (2f * spec.railInnerPaddingPx),
			expandedPx = contentWidthPx + (2f * spec.expandedInnerPaddingPx),
		),
	)
}

@PublishedApi
@Composable
internal fun rememberScaffoldPalette(colors: YesColors) = remember(colors) {
	ScaffoldPalette(
		navigation = ScaffoldGlassStyle(
			blurStyle = colors.navigationBlurStyle(),
			gradientStart = colors.navigationGradientStartColor(),
			gradientEnd = colors.navigationGradientEndColor(),
		),
		button = ScaffoldGlassStyle(
			blurStyle = colors.controlBlurStyle(),
			gradientStart = colors.controlGradientStartColor(),
			gradientEnd = colors.controlGradientEndColor(),
		),
		pageSurfaceColor = colors.surface,
	)
}

@PublishedApi
@Composable
internal fun ScaffoldGlassStyle.blurColors() = blurStyle.blurColors()
