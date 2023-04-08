package io.ezard.manuscript.library

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun GroupName(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = name)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "",
            modifier = Modifier.size(20.dp),
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Group(
    name: String,
    block: @Composable ManuscriptLibraryGroupScope.() -> Unit,
) {
    val scope = remember { object : ManuscriptLibraryGroupScope {} }
    Column {
        var expanded by remember(name) { mutableStateOf(false) }
        Surface(shape = RoundedCornerShape(4.dp), elevation = 4.dp) {
            Column {
                Surface(onClick = { expanded = !expanded }) {
                    GroupName(name = name)
                }
                AnimatedVisibility(visible = expanded) {
                    Column {
                        block(scope)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun GroupPreview() {
    ManuscriptLibrary {
        Group(name = "Group 1") {
            Component(name = "Component 1") {}
            Component(name = "Component 2") {}
            Component(name = "Component 3") {}
        }
    }
}
