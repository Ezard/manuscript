package io.ezard.manuscript.controls

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

@Composable
internal fun IntControl(control: Control<Int>) {
    var number by control
    TextField(
        value = number.toString(),
        onValueChange = { number = it.toIntOrNull() ?: 0 },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}
