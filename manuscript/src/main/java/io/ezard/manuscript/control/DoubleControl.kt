package io.ezard.manuscript.control

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.KeyboardType
import io.ezard.manuscript.manuscript.ManuscriptScope

@Composable
fun ManuscriptScope.control(name: String, defaultValue: Double): Control<Double> {
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
internal fun DoubleControl(control: Control<Double>) {
    Control(control = control) {
        var number by control
        TextField(
            value = number.toString(),
            onValueChange = { number = it.toDoubleOrNull() ?: 0.0 },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
    }
}
