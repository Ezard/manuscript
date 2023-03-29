package io.ezard.manuscript.control

import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.ezard.manuscript.manuscript.ManuscriptScope

@Composable
fun ManuscriptScope.control(name: String, defaultValue: Boolean): Control<Boolean> {
    val state = remember { mutableStateOf(defaultValue) }
    return remember {
        registerControl(
            Control(
                name = name,
                state = state,
            ),
        )
    }
}

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
