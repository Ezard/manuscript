package io.ezard.manuscript.controls

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

@Composable
internal fun FloatControl(control: Control<Float>) {
    Control(control = control) {
        var number by control
        TextField(
            value = number.toString(),
            onValueChange = { number = it.toFloatOrNull() ?: 0f },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
    }
}
