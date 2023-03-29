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

@Composable
fun ManuscriptLibrary(block: @Composable ManuscriptLibraryScope.() -> Unit) {
    ManuscriptTheme {
        var selectedComponent: Pair<String, @Composable () -> Unit>? by remember {
            mutableStateOf(null)
        }
        val scope = remember {
            ManuscriptLibraryScopeInstance { component ->
                selectedComponent = component
            }
        }
        val component = selectedComponent
        if (component == null) {
            Scaffold(topBar = { LibraryTopAppBar() }) { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues).padding(horizontal = 8.dp)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    block(scope)
                }
            }
        } else {
            CompositionLocalProvider(
                LocalManuscriptComponentName provides component.first,
                LocalManuscriptLibraryScope provides scope,
            ) {
                component.second()
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
        Group(name = "Progress Indicators") {
            Component(name = "Progress Bar") {}
            Component(name = "Progress Circle") {}
        }
    }
}
