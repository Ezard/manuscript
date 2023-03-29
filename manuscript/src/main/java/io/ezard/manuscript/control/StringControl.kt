package io.ezard.manuscript.control

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.ezard.manuscript.manuscript.ManuscriptScope

@Composable
fun ManuscriptScope.control(name: String, defaultValue: String): Control<String> {
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
internal fun StringControl(control: Control<String>) {
    Control(control = control) {
        var text by control
        TextField(
            value = text,
            onValueChange = { text = it },
        )
    }
}
