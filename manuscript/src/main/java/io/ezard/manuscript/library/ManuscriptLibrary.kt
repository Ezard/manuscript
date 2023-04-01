package io.ezard.manuscript.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.theme.LocalManuscriptComponentName
import io.ezard.manuscript.theme.ManuscriptTheme

@Composable
private fun LibraryTopAppBar() {
    TopAppBar(title = { Text(text = "Component Library") })
}

/**
 * Composable to create a browsable library of components
 *
 * @param [defaultDarkTheme] optional configuration to determine whether Manuscript should display components in light or dark mode by default
 *
 * Overridden by the `darkTheme` parameter of [Manuscript][io.ezard.manuscript.manuscript.Manuscript], if defined
 *
 * @param [defaultComponentTheme] optional configuration to allow each Manuscript component to be wrapped in your theme, including a parameter for whether Manuscript is displaying components in light or dark mode
 *
 * @sample [io.ezard.manuscript.library.ManuscriptLibrarySample]
 */
@Composable
fun ManuscriptLibrary(
    defaultDarkTheme: Boolean? = null,
    defaultComponentTheme: @Composable (
        darkTheme: Boolean,
        block: @Composable () -> Unit,
    ) -> Unit = { _, content -> content() },
    block: @Composable ManuscriptLibraryScope.() -> Unit,
) {
    ManuscriptTheme {
        var selectedComponent: Pair<String, @Composable () -> Unit>? by remember {
            mutableStateOf(null)
        }

        val data = ManuscriptLibraryData(
            defaultLibraryDarkTheme = defaultDarkTheme,
            defaultComponentTheme = defaultComponentTheme,
        ) { component -> selectedComponent = component }

        val scope = remember { object : ManuscriptLibraryScope {} }

        val component = selectedComponent
        CompositionLocalProvider(LocalManuscriptLibraryData provides data) {
            if (component == null) {
                Scaffold(topBar = { LibraryTopAppBar() }) { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues).padding(horizontal = 8.dp)) {
                        Spacer(modifier = Modifier.height(16.dp))
                        block(scope)
                    }
                }
            } else {
                CompositionLocalProvider(LocalManuscriptComponentName provides component.first) {
                    component.second()
                }
            }
        }
    }
}

@Preview
@Composable
private fun ManuscriptLibraryPreview() {
    ManuscriptLibrary {
        Group(name = "Buttons") {
            Component(name = "Rectangular Button") {}
            Component(name = "Circular Button") {}
        }
        Group(name = "Charts") {
            Component(name = "Bar Chart") {}
            Component(name = "Line Chart") {}
            Component(name = "Pie Chart") {}
        }
        Component(name = "Progress Indicator") {}
    }
}

@Composable
private fun ManuscriptLibrarySample() {
    ManuscriptLibrary {
        Group(name = "Buttons") {
            Component(name = "Rectangular Button") {
                // Manuscript composable goes here
            }
            Component(name = "Circular Button") {
                // Manuscript composable goes here}
            }
            Component(name = "Progress Indicator") {
                // Manuscript composable goes here}
            }
        }
    }
}
