package io.ezard.manuscript.bottomsheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ezard.manuscript.theme.ManuscriptTheme

@Composable
internal fun Placeholder(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
    )
}

@Preview
@Composable
private fun PlaceholderPreview() {
    ManuscriptTheme {
        Placeholder(text = "Text")
    }
}
