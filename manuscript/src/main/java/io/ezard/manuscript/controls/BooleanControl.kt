package io.ezard.manuscript.controls

import androidx.compose.material.Switch
import androidx.compose.runtime.Composable

@Composable
internal fun BooleanControl(control: Control<Boolean>) {
    var checked by control
    Switch(
        checked = checked,
        onCheckedChange = { checked = it },
    )
}
