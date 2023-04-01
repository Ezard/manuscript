package io.ezard.manuscript.controls

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable

@Composable
internal fun StringControl(control: Control<String>) {
    Control(control = control) {
        var text by control
        TextField(
            value = text,
            onValueChange = { text = it },
        )
    }
}
