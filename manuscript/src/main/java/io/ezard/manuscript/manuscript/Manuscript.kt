package io.ezard.manuscript.manuscript

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.bottomsheet.BottomSheet
import io.ezard.manuscript.controls.*
import io.ezard.manuscript.library.LocalManuscriptLibraryData
import io.ezard.manuscript.theme.*
import io.ezard.manuscript.utils.BackPressHandler
import io.ezard.manuscript.variant.Variant

@Composable
private fun ManuscriptScope.RegisterDefaultControls() {
    Control(type = Boolean::class) { control -> BooleanControl(control = control) }
    Control(type = Double::class) { control -> DoubleControl(control = control) }
    Control(type = Float::class) { control -> FloatControl(control = control) }
    Control(type = Int::class) { control -> IntControl(control = control) }
    Control(type = String::class) { control -> StringControl(control = control) }
}

@Composable
private fun PreviewModeManuscript(variants: List<Variant>) {
    Column {
        variants.forEachIndexed { index, variant ->
            variant.block()
            if (index < variants.lastIndex) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun getBottomSheetPeekHeight(): Dp {
    return with(LocalDensity.current) {
        MaterialTheme.typography.h5.fontSize.toDp() + 40.dp
    }
}

@Composable
private fun ManuscriptTabs(
    variants: List<Variant>,
    selectedVariantIndex: Int,
    onVariantSelected: (variantIndex: Int) -> Unit,
) {
    ScrollableTabRow(
        selectedTabIndex = selectedVariantIndex,
        modifier = Modifier.fillMaxWidth(),
    ) {
        variants.forEachIndexed { index, variant ->
            Tab(
                text = { Text(text = variant.name) },
                selected = selectedVariantIndex == index,
                onClick = { onVariantSelected(index) },
            )
        }
    }
}

@Suppress("SameParameterValue")
private fun <T> tryWithDefault(defaultValue: T, block: () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        defaultValue
    }
}

@Composable
private fun ComponentTheme(content: @Composable () -> Unit) {
    LocalManuscriptLibraryData.current.defaultComponentTheme(
        darkTheme = LocalManuscriptComponentTheme.current.darkTheme,
        content = content,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ColumnScope.ComponentWrapper(
    bottomSheetState: BottomSheetState,
    content: @Composable () -> Unit,
) {
    var wrapperHeight by remember { mutableStateOf(0) }
    var startBottomSheetOffset by remember { mutableStateOf(0f) }
    val bottomSheetOffset = tryWithDefault(0f) { bottomSheetState.requireOffset() }
    if (bottomSheetOffset > 0f && startBottomSheetOffset == 0f) {
        startBottomSheetOffset = bottomSheetState.requireOffset()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .onSizeChanged { size -> wrapperHeight = size.height },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    with(LocalDensity.current) {
                        (wrapperHeight - (startBottomSheetOffset - bottomSheetOffset)).toDp()
                    },
                ),
        ) {
            ComponentTheme(content = content)
        }
    }
}

@Composable
private fun getDefaultDarkTheme(defaultComponentDarkTheme: Boolean?): Boolean {
    val defaultLibraryDarkTheme = LocalManuscriptLibraryData.current.defaultLibraryDarkTheme
    return when {
        defaultComponentDarkTheme != null -> defaultComponentDarkTheme
        defaultLibraryDarkTheme != null -> defaultLibraryDarkTheme
        else -> isSystemInDarkTheme()
    }
}

/**
 * Composable to enable usage of variants / controls / actions
 *
 * @param [darkTheme] whether this component should be displayed using a dark background by default
 *
 * Overrides the `defaultDarkTheme` parameter for [ManuscriptLibrary][io.ezard.manuscript.library.ManuscriptLibrary]
 *
 * Defaults to [isSystemInDarkTheme] if this value is not specified at either the library level or the component level
 *
 * @sample [io.ezard.manuscript.manuscript.ManuscriptSample]
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InternalManuscriptScope.Manuscript(
    darkTheme: Boolean? = null,
    block: @Composable ManuscriptScope.() -> Unit,
) {
    CompositionLocalProvider(LocalManuscriptData provides ManuscriptData()) {
        val scope = remember { object : ManuscriptScope {} }
        val data = LocalManuscriptData.current

        with(scope) { RegisterDefaultControls() }

        block(scope)

        if (LocalInspectionMode.current) {
            PreviewModeManuscript(variants = data.variants)
            return@CompositionLocalProvider
        }

        val manuscriptLibraryData = LocalManuscriptLibraryData.current
        BackPressHandler { manuscriptLibraryData.onComponentSelected(null) }

        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed),
        )
        val initialDarkTheme = getDefaultDarkTheme(defaultComponentDarkTheme = darkTheme)
        var isComponentInDarkTheme by remember { mutableStateOf(initialDarkTheme) }
        val backgroundColour = when {
            isComponentInDarkTheme -> DarkColours.background
            else -> LightColours.background
        }
        ManuscriptTheme {
            CompositionLocalProvider(
                LocalManuscriptComponentTheme provides ManuscriptComponentTheme(
                    isDarkTheme = isComponentInDarkTheme,
                ),
            ) {
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    backgroundColor = backgroundColour,
                    topBar = {
                        ManuscriptTopAppBar(
                            onBackPressed = {
                                manuscriptLibraryData.onComponentSelected(null)
                            },
                            onDarkThemeChange = { updatedDarkTheme ->
                                isComponentInDarkTheme = updatedDarkTheme
                            },
                        )
                    },
                    sheetPeekHeight = getBottomSheetPeekHeight(),
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    sheetContent = {
                        val actions by data.actions.collectAsState()
                        BottomSheet(
                            controls = data.controls,
                            actions = actions,
                            bottomSheetState = scaffoldState.bottomSheetState,
                        )
                    },
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                    ) {
                        var selectedVariantIndex by remember { mutableStateOf(0) }
                        ManuscriptTabs(
                            variants = data.variants,
                            selectedVariantIndex = selectedVariantIndex,
                            onVariantSelected = { variantIndex ->
                                selectedVariantIndex = variantIndex
                            },
                        )
                        ComponentWrapper(bottomSheetState = scaffoldState.bottomSheetState) {
                            data.variants[selectedVariantIndex].block()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Manuscript(darkTheme: Boolean? = null, block: @Composable ManuscriptScope.() -> Unit) {
    with(object : InternalManuscriptScope {}) {
        Manuscript(
            darkTheme = darkTheme,
            block = block,
        )
    }
}

@Composable
private fun ManuscriptSample() {
    Manuscript {
        Variant("Button") {
            Button(onClick = {}) {
                Text(text = "Click me!")
            }
        }
    }
}
