package io.ezard.manuscript.control

import androidx.compose.material.Switch
import androidx.compose.runtime.Composable

@Composable
internal fun BooleanControl(control: Control<Boolean>) {
    Control(control = control) {
        var checked by control
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
        )
    }
}
