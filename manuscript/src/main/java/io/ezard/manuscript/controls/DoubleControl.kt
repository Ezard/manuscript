package io.ezard.manuscript.controls

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

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
