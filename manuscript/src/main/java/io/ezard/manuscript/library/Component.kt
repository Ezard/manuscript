package io.ezard.manuscript.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Suppress("unused")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ManuscriptLibraryScope.Component(
    name: String,
    block: @Composable () -> Unit,
) {
    val data = LocalManuscriptLibraryData.current
    Column {
        Surface(
            shape = RoundedCornerShape(4.dp),
            elevation = 4.dp,
            onClick = { data.onComponentSelected(name to block) },
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Suppress("unused")
@Composable
fun ManuscriptLibraryGroupScope.Component(
    name: String,
    block: @Composable () -> Unit,
) {
    val data = LocalManuscriptLibraryData.current
    Box(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(),
            onClick = { data.onComponentSelected(name to block) },
        ),
    ) {
        Text(
            text = name,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(start = 32.dp, top = 16.dp, bottom = 16.dp),
        )
    }
}

@Preview
@Composable
private fun ComponentPreview() {
    ManuscriptLibrary {
        Component(name = "Component") {}
    }
}

@Preview
@Composable
private fun ComponentInGroupPreview() {
    ManuscriptLibrary {
        Group(name = "Group") {
            Component(name = "Component") {}
        }
    }
}
