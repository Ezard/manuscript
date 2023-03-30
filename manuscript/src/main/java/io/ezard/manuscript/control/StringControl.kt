package io.ezard.manuscript.control

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
